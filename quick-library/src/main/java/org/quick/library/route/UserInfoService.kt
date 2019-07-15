package org.quick.library.route

import org.quick.library.model.UserInfoModel

interface UserInfoService {

    fun getUserInfo(listener: (model: UserInfoModel) -> Unit)
}