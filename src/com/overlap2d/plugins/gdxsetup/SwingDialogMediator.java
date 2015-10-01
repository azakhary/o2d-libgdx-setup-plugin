package com.overlap2d.plugins.gdxsetup;

import com.puremvc.patterns.mediator.SimpleMediator;
import com.puremvc.patterns.observer.Notification;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;


/**
 * Created by azakhary on 10/1/2015.
 */
public class SwingDialogMediator extends SimpleMediator {
    private static final String TAG = SwingDialogMediator.class.getCanonicalName();
    public static final String NAME = TAG;

    private GdxSetupPlugin plugin;

    public SwingDialogMediator(GdxSetupPlugin plugin) {
        super(NAME, null);
        this.plugin = plugin;
    }

    @Override
    public String[] listNotificationInterests() {
        return new String[]{
                GdxSetupPlugin.PANEL_OPEN
        };
    }

    @Override
    public void handleNotification(Notification notification) {
        super.handleNotification(notification);
        switch (notification.getName()) {
            case GdxSetupPlugin.PANEL_OPEN:
                String pluginPath = plugin.getAPI().getPluginDir();
                String tmpPath = pluginPath + File.separator + "tmp";
                String jarPath = tmpPath + File.separator + "gdx-setup.jar";

                String downloadPath = "http://overlap2d.com/plugin_data/gdx-setup.jar";

                File jarFile = new File(jarPath);
                try {
                    FileUtils.forceMkdir(new File(tmpPath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(!jarFile.exists()) {
                    try {
                        URL downloadUrl = new URL(downloadPath);
                        FileUtils.copyURLToFile(downloadUrl, jarFile);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if(jarFile.exists()) {
                    try {
                        Runtime.getRuntime().exec(" java -jar " + jarPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                break;
        }
    }
}
