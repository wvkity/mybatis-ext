package com.wkit.lost.mybatis.generator.ui

import com.wkit.lost.mybatis.generator.constants.Page
import javafx.animation.TranslateTransition
import javafx.beans.value.ObservableValue
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Control
import javafx.scene.control.TextField
import javafx.scene.control.TreeItem
import javafx.stage.Modality
import javafx.stage.Stage
import javafx.util.Duration
import org.apache.logging.log4j.LogManager
import java.io.IOException
import java.lang.ref.SoftReference

abstract class AbstractController : Parent(), Initializable {

    companion object {
        private val WINDOW_NODE_CACHE = HashMap<Page, SoftReference<AbstractController>>()
        private val LOG = LogManager.getLogger(AbstractController)
        const val SELECTOR_ERROR = "error"
    }

    /**
     * 顶级窗口
     */
    lateinit var primaryStage: Stage

    /**
     * 弹框窗口对象
     */
    protected lateinit var layer: Stage

    /**
     * 加载窗口
     * @param page 资源枚举对象
     * @param title 窗口标题
     * @param cache 是否从缓存中加载
     */
    fun loadWindow(page: Page, title: String, cache: Boolean): AbstractController? {
        val windowParentNodeRef = WINDOW_NODE_CACHE[page]
        if (cache) {
            windowParentNodeRef ?: run {
                return this
            }
        }
        val location = Thread.currentThread().contextClassLoader.getResource(page.getLocation())
        val loader = FXMLLoader(location)
        try {
            val parentWindow = loader.load<Parent>()
            val controller = loader.getController<AbstractController>()
            layer = Stage()
            layer.title = title
            layer.initModality(Modality.APPLICATION_MODAL)
            layer.initOwner(primaryStage)
            layer.scene = Scene(parentWindow)
            layer.isMaximized = false
            layer.isResizable = false
            layer.show()
            controller.layer = layer
            // 缓存
            val softReference = SoftReference<AbstractController>(controller)
            WINDOW_NODE_CACHE[page] = softReference
            return controller
        } catch (e: IOException) {
            LOG.error(e.message, e)
        }
        return null
    }

    /**
     * 显示窗口
     */
    fun show() {
        layer.run {
            this.show()
        }
    }

    /**
     * 关闭窗口
     */
    fun close() {
        layer.run {
            this.close()
        }
    }
    
    
    fun hasErrorSelector(node: Node?): Boolean {
        return hasClassSelector(node, SELECTOR_ERROR)
    }
    
    fun hasClassSelector(node: Node?, selector: String): Boolean {
        node?.run {
            node.styleClass.contains(selector)
        }
        return false
    }

    /**
     * 添加error类选择器
     * @param node 节点对象
     */
    fun error(node: Node?) {
        addClass(node, SELECTOR_ERROR)
    }
    
    fun addClass(node: Node?, selector: String) {
        node?.run {
            val styleClass = node.styleClass
            if (!styleClass.contains(selector)) {
                styleClass.add(selector)
            }
        }
    }

    /**
     * 删除error类选择器
     * @param node 节点对象
     */
    fun removeError(node: Node?) {
        removeClass(node, SELECTOR_ERROR)
    }
    
    fun removeClass(node: Node?, selector: String) {
        node?.run {
            node.styleClass.remove(selector)
        }
    }

    /**
     * 校验
     * @param node 节点对象
     */
    fun validate(node: Control?): Boolean {
        return node.takeIf { 
            it != null
        } ?.run {
            if (this is TextField) {
                return validate(this, this.text)
            }
            true
        } ?: run {
            return true
        }
    }

    fun validate(node: Control?, value: String?): Boolean {
        return value.takeIf {
            it.isNullOrBlank()
        }?.run {
            error(node)
            false
        } ?: run {
            removeError(node)
            true
        }
    }

    /**
     * 文本输入框值改变监听
     * @param node TextField对象
     */
    fun changedEventListener(node: TextField?) {
        changedEventListener(node, null)
    }

    /**
     * 文本输入框值改变监听
     * @param node TextField对象
     * @param callback 回调函数
     */
    fun changedEventListener(node: TextField?, callback: ((observable: ObservableValue<out String>, oldValue: String, newValue: String) -> Unit)?) {
        node?.run {
            val that = this
            callback?.run {
                that.textProperty().addListener { observable, oldValue, newValue ->
                    this(observable, oldValue, newValue)
                }
            } ?: run {
                this.textProperty().addListener { _, _, value ->
                    validate(this, value)
                }
            }
        }
    }

    /**
     * 动画切换
     */
    protected open fun toggle(node: Node) {
        val transition = TranslateTransition(Duration.millis(300.0), node)
        node.takeIf {
            it.isVisible
        } ?.run {
            // 隐藏
            transition.fromY = 0.0
            transition.toY = 165.0
            transition.setOnFinished {
                run {
                    this.isVisible = false
                    this.isManaged = false
                }
            }
        } ?: run {
            // 显示
            node.isVisible = true
            node.isManaged = true
            transition.fromY = 165.0
            transition.toY = 0.0
        }
        transition.play()
    }
}