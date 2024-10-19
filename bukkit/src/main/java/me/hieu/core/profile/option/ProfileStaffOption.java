package me.hieu.core.profile.option;

import lombok.Getter;
import lombok.Setter;

/**
 * Author: Le Thanh Hieu
 * Date: 13/10/2024
 */

@Getter @Setter
public class ProfileStaffOption {

    private boolean staffChatEnabled;

    public ProfileStaffOption(){
        staffChatEnabled = true;
    }

}
