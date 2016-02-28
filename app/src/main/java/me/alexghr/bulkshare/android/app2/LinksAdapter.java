package me.alexghr.bulkshare.android.app2;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import java.util.ArrayList;
import java.util.List;

import me.alexghr.bulkshare.android.app2.db.Link;

public class LinksAdapter extends BaseAdapter {

    private final Context context;
    private final List<SelectableItem> items;

    public LinksAdapter(Context context, List<Link> links) {
        this.context = context;

        this.items = new ArrayList<SelectableItem>();
        for (Link link : links) {
            this.items.add(SelectableItem.createItem(link));
        }
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public SelectableItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).link.getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        SelectableItem selectableItem = getItem(position);
        Link link = selectableItem.link;
        ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.link_item, parent, false);

            holder = new ViewHolder();
            holder.ctvLink = (CheckedTextView) view.findViewById(R.id.ctvLink);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        StringBuilder builder = new StringBuilder();

        boolean hasTitle = !link.getTitle().isEmpty();
        if (hasTitle) {
            builder.append("<big>").append(link.getTitle()).append("</big> - <small>");
        }

        builder.append(link.getLink());

        if (hasTitle) {
            builder.append("</small>");
        }

        holder.ctvLink.setText(Html.fromHtml(builder.toString()));
        holder.ctvLink.setChecked(selectableItem.selected);

        return view;
    }

    public void onItemClick(View view, int position) {
        ((ViewHolder) view.getTag()).ctvLink.toggle();

        SelectableItem item = getItem(position);
        item.selected = !item.selected;
    }

    public List<Link> getSelectedLinks() {
        List<Link> selectedLinks = new ArrayList<Link>();
        for (SelectableItem item : items) {
            if (item.selected) {
                selectedLinks.add(item.link);
            }
        }

        return selectedLinks;
    }

    public Link getLink(int position) {
        return getItem(position).link;
    }

    private static class ViewHolder {

        public CheckedTextView ctvLink;
    }

    private static final class SelectableItem {

        public Link link;
        public boolean selected = true;

        public static SelectableItem createItem(Link link) {
            SelectableItem selectableItem = new SelectableItem();

            selectableItem.link = link;
            selectableItem.selected = true;

            return selectableItem;
        }
    }

}
