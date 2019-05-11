package com.diabin.latte.deleggate;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.diabin.latte.app.Latte;
import com.diabin.latte.ui.camera.CameraImageBean;
import com.diabin.latte.ui.camera.LatteCamera;
import com.diabin.latte.ui.camera.RequestCode;
import com.diabin.latte.util.callback.CallBackType;
import com.diabin.latte.util.callback.CallbackManager;
import com.diabin.latte.util.callback.IGlobalCallback;
import com.yalantis.ucrop.UCrop;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Copyright (C)
 * 文件名称: PermissionCheckerDelegate
 * 创建人: 345
 * 创建时间: 2019/4/16 13:35
 * 描述: 中间层，用来进行权限的 判定
 * @author Lv
 */

/**
 *      @RuntimePermissions :这是必须使用的注解，用于标注你想要的申请权限的Activity或者Fragment，
 *      @NeedsPermission(Manifest.permission.CAMERA) ：这个也是必须要使用的注解，
 *  用于标注你需要获取权限的方法，注解括号里面有个参数，传入想要申请得权限，可以传入多个。当获得了
 *  对应的权限后就会执行这个方法。
 *      @OnShowRationale(Manifest.permission.CAMERA) ：这个不是必须的注解，用于标注申请权限
 *  时需要执行的方法，传入想要申请的权限，还需要一个PermissionRequest对象，这个对象有两种方法：
 *  proceed()让权限继续请求，canncel()让请求中断。也就是说，这个方法会拦截你发出的请求，这个方法
 *  用于告诉你接下来申请权限是干啥的，说服用户给你权限
 *      @OnPermissionDenied注解：这个也不是必须的注解，用于标注如果权限请求失败，
 *  但是用户没有勾选不再询问的时候执行的方法，注解括号里面有参数，传入想要申请的权限。
 *  也就是说，我们可以在这个方法做申请权限失败之后的处理，如像用户解释为什么要申请，或者重新申请操作等。
 *
 *
 */

@SuppressWarnings("ALL")
@RuntimePermissions
public abstract class PermissionCheckerDelegate extends BaseDelegate {
    /**
     * 不是直接调用方法，他仅仅只是为了生成代码而存在的
     */
    @NeedsPermission(Manifest.permission.CAMERA)
    void startCamera() {
        LatteCamera.start(this);
    }

    public void  start(){
        LatteCamera.start(this);
    }

    /**
     * 这个时代真正调用的方法
     */
    public void startCameraWithCheck() {
        PermissionCheckerDelegatePermissionsDispatcher.startCameraWithPermissionCheck(this);
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    void onCamerDenied() {
        Toast.makeText(getContext(), "不允许拍照", Toast.LENGTH_SHORT).show();
    }

    void onCamerNever() {
        Toast.makeText(getContext(), "永久拒绝权限", Toast.LENGTH_SHORT).show();
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    void onCameraReational(PermissionRequest request) {
        showRetionaeDialog(request);
    }

    private void showRetionaeDialog(final PermissionRequest request) {
        new AlertDialog.Builder(getContext())
                .setPositiveButton("同意使用", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("拒绝使用", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage("权限管理")
                .show();
    }

    /**
     *      Rebuild 之后会生成一个辅助类，用来调用被注解的Activity的方法，所以，第一次使用的话
     *  注解添加完后 要Rebuild 一次。否则不能生成辅助类。
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] data) {
        super.onRequestPermissionsResult(requestCode, permissions, data);
        PermissionCheckerDelegatePermissionsDispatcher
                .onRequestPermissionsResult(this, requestCode, data);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RequestCode.TAKE_PHOTO:
                    final Uri resultUri = CameraImageBean.getInstance().getPath();
                    //剪裁图片，第一个为原始路径，第二个为要保存的路径，
                    UCrop.of(resultUri,resultUri)
                            .withMaxResultSize(400,400)
                            .start(getContext(),this);
                    break;
                case RequestCode.PICK_PHOTO:
                    if (data != null){
                        //从相册取的 原路径
                        final Uri pickPath = data.getData();
                        //从相册选择后需要有个路径存放剪裁后的图片
                        final String pickCropPath = LatteCamera.createCropFile().getPath();
                        UCrop.of(pickPath,Uri.parse(pickCropPath))
                                .withMaxResultSize(400,400)
                                .start(getContext(),this);
                    }
                    break;
                case RequestCode.CROP_PHOTO:
                    System.out.println("00000000000000000000000000000");
                    final Uri cropUri = UCrop .getOutput(data);
                    //拿到剪裁后的数据进行处理
                    final IGlobalCallback<Uri> callback = CallbackManager
                            .getInstance()
                            .getCallBack(CallBackType.ON_CROP);
                    if (callback != null){
                        callback.executeCallBack(cropUri);
                    }
                    break;
                case RequestCode.CROP_ERROR:
                    Toast.makeText(getContext(), "剪裁出错", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }
}
