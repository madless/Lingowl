package ua.madless.lingowl.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ua.madless.lingowl.R;
import ua.madless.lingowl.core.model.converted_server_response.ConvertedResponseAdapterItem;
import ua.madless.lingowl.core.util.StringConverter;

/**
 * Created by User on 21.02.2016.
 */
public class TranslationsListAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<ConvertedResponseAdapterItem> convertedResponses = new ArrayList<>();

    public TranslationsListAdapter(Context context, ArrayList<ConvertedResponseAdapterItem> convertedResponses) {
        this.context = context;
        this.convertedResponses = convertedResponses;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return convertedResponses.size();
    }

    @Override
    public Object getItem(int position) {
        return convertedResponses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        ConvertedResponseAdapterItem item  = convertedResponses.get(position);
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_translations_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String partOfSpeech = StringConverter.getShorterPartOfSpeech(item.getPartOfSpeech());
        String gender = StringConverter.getShorterGender(item.getGender());
        holder.getTextViewWordTranslation().setText(item.getTranslation());
        holder.getTextViewWordPart().setText(partOfSpeech);
        holder.getTextViewGender().setText(gender);
        if(item.isSelected()) {
            holder.getImageViewSelectedTranslation().setVisibility(View.VISIBLE);
        } else {
            holder.getImageViewSelectedTranslation().setVisibility(View.GONE);
        }
        return convertView;
    }

    public class ViewHolder {
        private TextView textViewWordTranslation, textViewWordPart, textViewGender;
        private ImageView imageViewSelectedTranslation;
        public ViewHolder(View itemView) {
            textViewWordTranslation = (TextView) itemView.findViewById(R.id.textViewWordTranslation);
            textViewWordPart = (TextView) itemView.findViewById(R.id.textViewWordPart);
            textViewGender = (TextView) itemView.findViewById(R.id.textViewGender);
            imageViewSelectedTranslation = (ImageView) itemView.findViewById(R.id.imageViewSelectedTranslation);
        }

        public TextView getTextViewWordTranslation() {
            return textViewWordTranslation;
        }

        public TextView getTextViewWordPart() {
            return textViewWordPart;
        }

        public TextView getTextViewGender() {
            return textViewGender;
        }

        public ImageView getImageViewSelectedTranslation() {
            return imageViewSelectedTranslation;
        }
    }

    public ConvertedResponseAdapterItem getSelectedTranslation() {
        for (ConvertedResponseAdapterItem item: convertedResponses) {
            if(item.isSelected()) {
                return item; // TODO: 27.04.2016 implement multiple selection!
            }
        }
        if(!convertedResponses.isEmpty()) {
            return convertedResponses.get(0);
        } else {
            return null;
        }
    }


}
