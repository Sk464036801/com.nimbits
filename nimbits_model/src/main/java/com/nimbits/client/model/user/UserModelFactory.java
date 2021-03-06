/*
 * Copyright (c) 2013 Nimbits Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either expressed or implied.  See the License for the specific language governing permissions and limitations under the License.
 */

package com.nimbits.client.model.user;

import com.nimbits.client.constants.Const;
import com.nimbits.client.model.entity.Entity;


public class UserModelFactory {


    private UserModelFactory() {
    }


    public static User createUserModel(final User u) {
        return new UserModel(u);
    }

    public static User createUnauthenticatedUserModel(final Entity entity) {

        return new UserModel(entity);
    }

    public static User createUserModel(final Entity entity, final String password, final String salt, final UserSource source) {


        return new UserModel(entity, password, salt, source);
    }

    public static LoginInfo createLoginInfo(String loginUrl, String logoutUrl, UserStatus userStatus, boolean isGAE) {
        return new LoginInfoImpl(loginUrl, logoutUrl, userStatus, isGAE);
    }

    public static LoginInfo createNullLoginInfo(boolean isGAE) {
        return new LoginInfoImpl("", Const.WEBSITE, UserStatus.unknown, isGAE);
    }


}
