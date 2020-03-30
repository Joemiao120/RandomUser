package android.myc.usergenerator.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ImageUtils {
    public static void saveImageToLocal(final Context context, final String imagePath, final String path, final String fileName) {

        Observable.create(new ObservableOnSubscribe<File>() {
            @Override
            public void subscribe(ObservableEmitter<File> e) throws Exception {
                e.onNext(Glide.with(context)
                        .load(imagePath)
                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get());
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Consumer<File>() {
                    @Override
                    public void accept(File file) throws Exception {

                        File pictureFolder = Environment.getExternalStorageDirectory();
                        File appDir = new File(pictureFolder, path);
                        if (!appDir.exists()) {
                            appDir.mkdirs();
                        }

                        File destFile = new File(appDir, fileName);
                        copy(file, destFile);

                        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                                Uri.fromFile(new File(destFile.getPath()))));
                    }
                });
    }

    public static void copy(File source, File target) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(source);
            fileOutputStream = new FileOutputStream(target);
            byte[] buffer = new byte[1024];
            while (fileInputStream.read(buffer) > 0) {
                fileOutputStream.write(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
