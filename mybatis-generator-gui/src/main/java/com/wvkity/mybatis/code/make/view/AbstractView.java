package com.wvkity.mybatis.code.make.view;

import com.wvkity.mybatis.code.make.observable.Listener;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;

import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Getter
@Setter
@Accessors(chain = true)
public abstract class AbstractView extends Parent implements Initializable, Listener {

    /**
     * 窗口缓存
     */
    private static final Map<View, SoftReference<AbstractView>> WINDOW_CACHE = new HashMap<>();

    /**
     * 顶级窗口对象
     */
    private Stage primaryStage;
    /**
     * 弹框窗口对象
     */
    private Stage layer;

    /**
     * 打开窗口
     */
    public void open() {
        Optional.ofNullable(this.layer).ifPresent(Stage::show);
    }

    /**
     * 打开窗口
     * @param view  {@link View}
     * @param title 窗口标题
     * @return {@link AbstractView}
     */
    public AbstractView open(final View view, final String title) {
        return open(view, title, true);
    }

    /**
     * 打开窗口
     * @param view  {@link View}
     * @param title 窗口标题
     * @param cache 是否从缓存中加载
     * @return {@link AbstractView}
     */
    public AbstractView open(final View view, final String title, final boolean cache) {
        final SoftReference<AbstractView> sr = WINDOW_CACHE.get(view);
        final AbstractView av;
        if (cache && sr != null && (av = sr.get()) != null) {
            return av;
        }
        try {
            final URL location = Thread.currentThread().getContextClassLoader().getResource(view.getLocation());
            final FXMLLoader loader = new FXMLLoader(location);
            final Parent parent = loader.load();
            final AbstractView it = loader.getController();
            layer = new Stage();
            layer.setTitle(title);
            layer.initModality(Modality.APPLICATION_MODAL);
            layer.initOwner(primaryStage);
            layer.setScene(new Scene(parent));
            layer.setResizable(false);
            layer.setMaximized(false);
            layer.show();
            it.setLayer(layer);
            if (cache) {
                final SoftReference<AbstractView> reference = new SoftReference<>(it);
                WINDOW_CACHE.put(view, reference);
            }
            return it;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 关闭弹框
     */
    public void close() {
        Optional.ofNullable(this.layer).ifPresent(Stage::close);
    }

}
