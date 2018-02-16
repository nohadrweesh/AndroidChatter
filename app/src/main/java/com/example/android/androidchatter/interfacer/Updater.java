package com.example.android.androidchatter.interfacer;

import com.example.android.androidchatter.typo.InfoOfFriends;
import com.example.android.androidchatter.typo.InfoOfMessage;

/**
 * Created by Speed on 14/02/2018.
 */

public interface Updater {
    void updateData(InfoOfMessage[]message, InfoOfFriends[] friends,InfoOfFriends []unApprovedFriends,String userKey);
}
