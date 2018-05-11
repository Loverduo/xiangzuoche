package com.ruixiangtong.common.utils;

import android.content.Context;


/**
 */
public class SharePreferceUtil {

    //静态对象
    private static SharePreferceUtil util;

    private PreferencesServer wrapper;

    public static SharePreferceUtil getPreferceUtil(Context context) {
        if (util == null) {
            util = new SharePreferceUtil(context);
        }
        return util;
    }

    private SharePreferceUtil(Context context) {
        wrapper = PreferencesServer.getWrapper(context);
    }

    /**
     * 是否不是首次登录
     */
    private static final String IS_FIRST_USE = "isFirstUse";
    /**
     * 是否展示登录引导页面
     */
    private static final String IS_SHOW_LOGIN_GUIDE = "isShowLoginGuide";
    /**
     * 是否显示隐私条款
     */
    private static final String IS_SHOW_PROTOCAL = "isShowProtocal";

    /**
     * 是否有更新
     */
    private static final String UPDATE_VERSION = "UpdateVersion";
    /**
     * 是否是首次进入主框架页面
     */
    private static final String IS_FIRST_IN_MAIN = "isFirstInMain";
    /**
     * 是否是首次打开第三方加密服务
     */
    private static final String IS_FIRST_OPEN_SEVER = "isFirstOpenSever";
    /**
     * 强制更新是否要删除数据库和当前升级后的版本号
     */
    private static final String IS_DELETE_DB = "isDeleteDb";

    private static final String DELETE_VERSION = "deleteVersion";

    public static final String AT_ACCOUNT = "at_account";

    /**
     * 需要弹出的强制退出的提示信息
     */
    public static final String LOGOUT_MESSAGE = "logout_message";

    /**
     * 安全锁开关
     */
    public static final String SAFELOCK = "safe_lock";

    /**
     * 锁屏锁定
     */
    public static final String SAFELOCK_TYPE_LOCKSCREEN = "safeLockType_lockScreen";

    /**
     * 后台运行锁定
     */
    public static final String SAFELOCK_TYPE_BACKGROUND = "safeLockType_backGround";


    /**
     * 设备信息
     */
    public static final String ALL_DEVICES_MESSAGE = "allDevicesMessage";


    /**
     * 绑定设备标识
     */
    public static final String IS_BIND_DEVICE = "bind_device";

    /**
     * 解绑设备
     */
    public static final String IS_UNBIND_DEVICE = "unbind_device";
    /**
     * 是否显示版本更新的new字样
     */
    public static final String IS_SHOW_NEW_VIEW = "is_show_new_view";
    /**
     * 用于保存检测到的版本
     */
    public static final String NEW_VERSION = "new_version";

    public boolean getIsShowNewView() {
        return wrapper.gPrefBooleanValue(IS_SHOW_NEW_VIEW, true);
    }

    public void setIsShowNewView(boolean isShow) {
        wrapper.setPreferenceBooleanValue(IS_SHOW_NEW_VIEW, isShow);
    }

    public String getNewVersion() {
        return wrapper.gPrefStringValue(NEW_VERSION);
    }

    public void setNewVersion(String version) {
        wrapper.setPreferenceStringValue(NEW_VERSION, version);
    }

    public String getLogoutMessage() {
        return wrapper.gPrefStringValue(LOGOUT_MESSAGE);
    }

    public void setLogoutMessage(String logoutMessage) {
        wrapper.setPreferenceStringValue(LOGOUT_MESSAGE, logoutMessage);
    }

    public String getAccount() {
        return wrapper.gPrefStringValue(AT_ACCOUNT);
    }

    public void setAccount(String account) {
        wrapper.setPreferenceStringValue(AT_ACCOUNT, account);
    }

    /**
     * 是否在登录界面之前的界面收到绑定设备通知
     */
    public void setIsBindDevice(boolean isFirstUse) {
        wrapper.setPreferenceBooleanValue(IS_BIND_DEVICE, isFirstUse);
    }

    public boolean getIsBindDevice() {
        return wrapper.gPrefBooleanValue(IS_BIND_DEVICE, false);
    }


    /**
     * 设备信息
     *
     * @param allDevicesMessage
     */
    public void setAllDevicesMessage(String allDevicesMessage) {
        wrapper.setPreferenceStringValue(ALL_DEVICES_MESSAGE, allDevicesMessage);
    }

    public String getAllDevicesMessage() {
        return wrapper.gPrefStringValue(ALL_DEVICES_MESSAGE);
    }

    /**
     * 是否首次使用
     */
    public void setIsFirstUse(boolean isFirstUse) {
        wrapper.setPreferenceBooleanValue(IS_FIRST_USE, isFirstUse);
    }

    public boolean getIsFirstUse() {
        return wrapper.gPrefBooleanValue(IS_FIRST_USE, true);
    }

    /**
     * 是否展示登录引导界面
     */
    public void setIsShowLoginGuide(boolean isShowLoginGuide) {
        wrapper.setPreferenceBooleanValue(IS_SHOW_LOGIN_GUIDE, isShowLoginGuide);
    }

    public boolean getIsShowLoginGuide() {
        return wrapper.gPrefBooleanValue(IS_SHOW_LOGIN_GUIDE, true);
    }

    /**
     * 是否在登录页面显示隐私条款的内容
     *
     * @param isShowProtocal
     */
    public void setIsShowProtocal(boolean isShowProtocal) {
        wrapper.setPreferenceBooleanValue(IS_SHOW_PROTOCAL, isShowProtocal);
    }

    public boolean getIsShowProtocal() {
        return wrapper.gPrefBooleanValue(IS_SHOW_PROTOCAL, true);
    }

    /**
     * 是否有更新
     */
    public void setUpdateVersion(String updateVersion) {
        wrapper.setPreferenceStringValue(UPDATE_VERSION, updateVersion);
    }

    public String getUpdateVersion() {
        return wrapper.gPrefStringValue(UPDATE_VERSION);
    }

    /**
     * 设置是否是首次进入主框架界面
     *
     * @param isFirst 是否是首次进入主框架界面
     */
    public void setIsFirstInMain(boolean isFirst) {
        wrapper.setPreferenceBooleanValue(IS_FIRST_IN_MAIN, isFirst);
    }

    /**
     * 获取是否是首次进入主框架界面
     *
     * @return 是否是首次进入主框架界面
     */
    public boolean getIsFirstInMain() {
        return wrapper.gPrefBooleanValue(IS_FIRST_IN_MAIN, true);
    }

    /**
     * 设置是否是首次打开第三方加密服务
     *
     * @param isFirst 是否是首次打开第三方加密服务
     */
    public void setIsFirstOpenSever(boolean isFirst) {
        wrapper.setPreferenceBooleanValue(IS_FIRST_OPEN_SEVER, isFirst);
    }

    /**
     * 获取是否是首次打开第三方加密服务
     *
     * @return 是否是首次打开第三方加密服务
     */
    public boolean getIsFirstOpenSever() {
        return wrapper.gPrefBooleanValue(IS_FIRST_OPEN_SEVER, true);
    }

    /**
     * 更新后是否删除数据库
     *
     * @param isDeleteDb
     */
    public void setIsDeleteDb(boolean isDeleteDb) {
        wrapper.setPreferenceBooleanValue(IS_DELETE_DB, isDeleteDb);
    }

    public boolean getIsDeleteDb() {
        return wrapper.gPrefBooleanValue(IS_DELETE_DB, false);
    }

    /**
     * 获取应该升级到的版本号
     *
     * @param deleteVersion
     */
    public void setDeleteVersion(String deleteVersion) {
        wrapper.setPreferenceStringValue(DELETE_VERSION, deleteVersion);
    }

    public String getDeleteVersion() {
        return wrapper.gPrefStringValue(DELETE_VERSION);
    }

    /**
     * 设置安全锁开关
     *
     * @param safeLockIsOpen 是否开启
     */
    public void setSafeLock(boolean safeLockIsOpen) {
        wrapper.setPreferenceBooleanValue(SAFELOCK, safeLockIsOpen);
    }

    /**
     * 获取安全锁开关
     *
     * @return
     */
    public boolean getSafeLock() {
        return wrapper.gPrefBooleanValue(SAFELOCK, false);
    }

    /**
     * 设置锁屏锁定开关
     *
     * @param safeLockIsOpen 是否开启
     */
    public void setSafeLockType_lockScreen(boolean safeLockIsOpen) {
        wrapper.setPreferenceBooleanValue(SAFELOCK_TYPE_LOCKSCREEN, safeLockIsOpen);
    }

    /**
     * 获取锁屏锁定开关
     *
     * @return
     */
    public boolean getSafeLockType_lockScreen() {
        return wrapper.gPrefBooleanValue(SAFELOCK_TYPE_LOCKSCREEN, false);
    }

    /**
     * 设置后台运行开关
     *
     * @param safeLockIsOpen 是否开启
     */
    public void setSafeLockType_backGround(boolean safeLockIsOpen) {
        wrapper.setPreferenceBooleanValue(SAFELOCK_TYPE_BACKGROUND, safeLockIsOpen);
    }

    /**
     * 获取后台运行开关
     *
     * @return
     */
    public boolean getSafeLockType_backGround() {
        return wrapper.gPrefBooleanValue(SAFELOCK_TYPE_BACKGROUND, true);
    }


}
