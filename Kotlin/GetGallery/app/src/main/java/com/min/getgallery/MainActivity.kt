package com.min.getgallery

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.net.toUri

class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE_GET_FILE_IMAGE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showPermissionDialog()
        findViewById<Button>(R.id.testBtn).setOnClickListener {
            onImageAddButton()
        }

    }

    fun showPermissionDialog() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(this, "외부 저장소 사용을 위해 읽기/쓰기 필요", Toast.LENGTH_SHORT).show()
                }

                val permissionArr = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions( permissionArr, 2)
            }
        }
    }

    fun onImageAddButton() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE

        //다중 이미지를 가져올 수 있도록 세팅
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        startActivityForResult(intent, REQUEST_CODE_GET_FILE_IMAGE)
    }

    private fun getRealPathFromURI(contentURI: Uri?): String? {
        val filePath: String?
        val cursor: Cursor? =
            contentResolver.query(contentURI!!, null, null, null, null)
        if (cursor == null) {
            filePath = contentURI.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            filePath = cursor.getString(idx)
            cursor.close()
        }
        return filePath
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_GET_FILE_IMAGE && resultCode == RESULT_OK) {
            val clipData = data?.clipData
            val clipDataSize = clipData?.itemCount

            if (clipData == null) { // 단일 이미지 선택
                val fileUri = data?.data!!

            } else {
                clipData.let { clipData -> // 복수 이미지 선택
                    for (i in 0 until clipDataSize!!) {
                        val fileUri = clipData.getItemAt(i).uri
//                        Utils.logE("dddddddd", fileUri.toString())
//                        Utils.logE("dddddddd", getPathFromUri(fileUri).toString())
                        Log.e("ddddddd", getRealPathFromURI(fileUri)!!.toUri().toString())
                        findViewById<ImageView>(R.id.testImage).setImageURI(getRealPathFromURI(fileUri)!!.toUri())
                    }
                }
            }
        }
    }
}