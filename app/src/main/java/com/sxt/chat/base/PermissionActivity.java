package com.sxt.chat.base;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xt.sun
 * 2020-03-04
 */
public class PermissionActivity extends AppCompatActivity {
    public boolean checkPermissions(int requestCode, String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> lackedPermission = new ArrayList<>();
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    lackedPermission.add(permission);
                }
            }
            if (lackedPermission.size() > 0) {
                requestPermissions(lackedPermission.toArray(new String[]{}), requestCode);
                return false;
            }
            return true;
        }
        return true;
    }

    public void goToAppSettingsPage() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    /**
     * 检测日历权限
     */
    protected void checkCalendarPermission(int requestCode) {
        String[] permissions = new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR};
        checkPermissions(requestCode, permissions);
    }

    /**
     * 检测相册权限，选择视频
     */
    protected void checkGalleryVideoPermission(int requestCode) {
        String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        boolean hasPermission = checkPermissions(requestCode, permissions);
        if (hasPermission) {
            openGalleryVideo(requestCode);
        }
    }

    /**
     * 检测相册权限，选择图片
     */
    protected void checkGalleryPermission(int requestCode) {
        String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        boolean hasPermission = checkPermissions(requestCode, permissions);
        if (hasPermission) {
            openGallery(requestCode);
        }
    }

    /**
     * 检测相册权限，选择图片
     */
    protected void checkCameraPermission(File file, int requestCode) {
        boolean permissions = checkPermissions(requestCode, new String[]{Manifest.permission.CAMERA});
        if (permissions) {
            openCamera(file, requestCode);
        }
    }

    /**
     * 打开相册
     */
    protected void openGallery(int requestCode) {
        Intent intent;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            intent = new Intent(Intent.ACTION_PICK);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        }
        //４．３以上的设备才支持Intent.EXTRA_ALLOW_MULTIPLE，是否可以一次选择多个文件
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        //返回的文件是否必须存在于设备上，而不是需要从远程服务下载的,用于解决用户选中的是云端文件时的问题
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, false);
        startActivityForResult(Intent.createChooser(intent, "选择图片"), requestCode);
    }

    /**
     * 打开系统拍照
     */
    protected void openCamera(File file, int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", file);
        //添加权限
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 打开相册 选择视频
     */
    protected void openGalleryVideo(int requestCode) {
        Intent intent;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            intent = new Intent();
            intent.setType("video/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            intent = new Intent(Intent.ACTION_PICK);
            intent.setDataAndType(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, "video/*");
        }
        //４．３以上的设备才支持Intent.EXTRA_ALLOW_MULTIPLE，是否可以一次选择多个文件
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        //返回的文件是否必须存在于设备上，而不是需要从远程服务下载的,用于解决用户选中的是云端文件时的问题
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, false);
        startActivityForResult(Intent.createChooser(intent, "选择视频"), requestCode);
    }

    private boolean hasAllPermissionsGranted(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (hasAllPermissionsGranted(grantResults)) {
            onPermissionsAllowed(requestCode, permissions, grantResults);
        } else {
            if (!shouldShowRequestPermissionRationale(permissions[0])) {
                onPermissionsRefusedNever(requestCode, permissions, grantResults);
            } else {
                onPermissionsRefused(requestCode, permissions, grantResults);
            }
        }
    }

    public void onPermissionsAllowed(int requestCode, String[] permissions, int[] grantResults) {
    }

    public void onPermissionsRefused(int requestCode, String[] permissions, int[] grantResults) {
    }

    public void onPermissionsRefusedNever(int requestCode, String[] permissions, int[] grantResults) {
    }

    /**
     * 权限提醒弹框
     */
    protected void showPermissionAlert(String message) {
//        new DialogBuilderAlert(this)
//                .setTitle("温馨提示")
//                .setMessage(message)
//                .setConfirm("去授权")
//                .setOnConfirmClickListener(dialog -> {
//                    dialog.dismiss();
//                    goToAppSettingsPage();
//                })
//                .show();
    }
}