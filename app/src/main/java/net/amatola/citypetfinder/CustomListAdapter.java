package net.amatola.citypetfinder;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

public class CustomListAdapter extends BaseAdapter {

    private Activity mActivity;
    private LayoutInflater mInflater;
    private List<LostPet> mLostPetList;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListAdapter(Activity activity, List<LostPet> lostPetList) {
        this.mActivity = activity;
        this.mLostPetList = lostPetList;
    }

    @Override
    public int getCount() {
        return mLostPetList.size();
    }

    @Override
    public Object getItem(int location) {
        return mLostPetList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return mLostPetList.get(position).getLostPetId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mInflater == null)
            mInflater = (LayoutInflater) mActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = mInflater.inflate(R.layout.list_row_pet, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.thumbnail);
        TextView petName = (TextView) convertView.findViewById(R.id.petName);
        TextView petBreed = (TextView) convertView.findViewById(R.id.petBreed);

        // getting movie data for the row
        LostPet m = mLostPetList.get(position);

        thumbNail.setImageUrl(m.getImageThumbnailUrl(), imageLoader);

        petName.setText(m.getPetName());

        petBreed.setText(m.getBreed());

        return convertView;
    }
}
