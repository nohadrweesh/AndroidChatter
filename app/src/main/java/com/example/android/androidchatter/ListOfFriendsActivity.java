package com.example.android.androidchatter;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.androidchatter.typo.InfoOfFriends;
import com.example.android.androidchatter.typo.InfoStatus;

public class ListOfFriendsActivity extends ListActivity {

    public static final int ADD_NEW_FRIEND= Menu.FIRST;
    public static final int EXIT=Menu.FIRST+1;

    public class FriendListAdapter extends BaseAdapter{
        public LayoutInflater inflater;
        public Bitmap onlineIcon;
        public Bitmap offlineIcon;
        InfoOfFriends[]friends;
        class ViewHolder{
            TextView text;
            ImageView icon;
        }
        public FriendListAdapter(Context context){
            inflater=LayoutInflater.from(context);
            onlineIcon= BitmapFactory.decodeResource(context.getResources(),R.drawable.online);
            offlineIcon= BitmapFactory.decodeResource(context.getResources(),R.drawable.offline);

        }
        public void setFriends(InfoOfFriends[]friends){
            this.friends=friends;
        }


        @Override
        public int getCount() {
            return friends.length;
        }

        @Override
        public InfoOfFriends getItem(int i) {
            return friends[i];
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView==null){
                convertView=inflater.inflate(R.layout.list_friend_screen,null);
                holder=new ViewHolder();
                holder.text=(TextView)convertView.findViewById(R.id.text);
                holder.icon=(ImageView)convertView.findViewById(R.id.icon);

                convertView.setTag(holder);

            }else{
                holder=(ViewHolder)convertView.getTag();
            }

            holder.text.setText(friends[pos].userName);
            holder.icon.setImageBitmap(friends[pos].status == InfoStatus.ONLINE ?onlineIcon:offlineIcon);
            return convertView;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_friends);
    }
}
