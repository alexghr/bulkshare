package me.alexghr.bulkshare.android.app2;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import me.alexghr.bulkshare.android.app2.db.DBAccess;
import me.alexghr.bulkshare.android.app2.db.Link;
import me.alexghr.bulkshare.android.app2.logic.LoadLinksTask;

public class LinksFragment extends ListFragment implements LoadLinksTask.OnFinishedLoadingListener {

    public static final String ARGUMENT_LIST_ID = "list_id";

    private static final int MENU_ITEM_ID_SEND = 0xfd1e;
    private static final int DEFAULT_LIST_ID = 1;

    private int listId = DEFAULT_LIST_ID;
    private LinksAdapter listAdapter;
    private List<Link> selectedLinks = null;

    public LinksFragment() {
        setHasOptionsMenu(true);
    }

    private void refreshList() {
        setListShown(false);
        new LoadLinksTask(getActivity(), this).execute(listId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {
            listId = arguments.getInt(ARGUMENT_LIST_ID, DEFAULT_LIST_ID);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        refreshList();
    }

    @Override
    public void onResume() {
        super.onResume();
        selectedLinks = null;
    }

    @Override
    public void onStop() {
        super.onStop();

        if (selectedLinks != null) {
            DBAccess access = new DBAccess(getActivity());
            for (Link link : selectedLinks) {
                access.setSentStatus(link.getId(), true);
            }
            selectedLinks = null;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.add(Menu.NONE, MENU_ITEM_ID_SEND, Menu.NONE, "Send").setIcon(android.R.drawable.ic_menu_send)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == MENU_ITEM_ID_SEND) {
            sendLinks();
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendLinks() {
        selectedLinks = listAdapter.getSelectedLinks();
        if (selectedLinks == null || selectedLinks.isEmpty()) {
            Toast.makeText(getActivity(), "Select something first", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder builder = new StringBuilder("<!DOCTYPE html><html><body>");

        for (Link link : selectedLinks) {
            // Doesn't work in Gmail 4.2.1 because it strips HTML
            //builder.append(String.format("<a href=\"%s\">%s</a><br/>", link.getLink(), link.getTitle()));

            String title = link.getTitle();
            if (title != null && !title.isEmpty()) {
                builder.append(title).append("<br>");
            }

            builder.append(link.getLink()).append("<br><br>");
        }

        builder.append("</body></html>");

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Stuff");
        intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(builder.toString()));

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String emails = settings.getString(getString(R.string.pref_email_cc_key), "");

        intent.putExtra(Intent.EXTRA_EMAIL, emails.split(" "));

        startActivity(Intent.createChooser(intent, "Select your email app"));
    }

    @Override
    public void finishedLoading(List<Link> links) {
        listAdapter = new LinksAdapter(getActivity(), links);
        setListAdapter(listAdapter);
        setListShown(true);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        listAdapter.onItemClick(v, position);
    }

    private void onListItemLongClick(final ListView parent, final View view, final int position, final long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(R.array.item_long_press_menu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onLongPressMenuItemClicked(which, parent, view, position, id);
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void onLongPressMenuItemClicked(int which, ListView parent, View v, int position, long id) {
        Link link = listAdapter.getLink(position);
        switch (which) {
            case 0:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(link.getLink()));
                startActivity(Intent.createChooser(intent, "Open link with"));
                break;

            case 1:
                ClipboardManager clipboardManager =
                        (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setPrimaryClip(ClipData.newPlainText("bulkshare", link.getLink()));
                break;

            case 2:
                DBAccess access = new DBAccess(getActivity());
                access.deleteLink(link);
                refreshList();
                break;

            case 3:
                onListItemClick(parent, v, position, id);
                break;

            default:
                break;
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setEmptyText("Nothing here yet.");

        ListView listView = getListView();
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                onListItemLongClick((ListView) parent, view, position, id);
                return true;
            }
        });
    }
}
