import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import androidx.compose.ui.window.ComposeUIViewController
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readValue
import platform.CoreGraphics.CGRectZero
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.WebKit.WKNavigationAction
import platform.WebKit.WKNavigationActionPolicy
import platform.WebKit.WKNavigationDelegateProtocol
import platform.WebKit.WKWebView
import platform.darwin.NSObject

fun MainViewController() = ComposeUIViewController {
    WebView(Modifier.fillMaxSize(), "https://github.com")
}

@OptIn(ExperimentalForeignApi::class)
@Composable
fun WebView(modifier: Modifier, url: String) {
    val rememberedNavigationDelegate = remember { WKNavigationDelegate() }
    UIKitView(
        modifier = modifier,
        factory = {
            WKWebView(
                frame = CGRectZero.readValue(),
            ).apply {
                navigationDelegate = rememberedNavigationDelegate
                loadRequest(
                    request = NSURLRequest(
                        uRL = NSURL(string = url),
                    )
                )
            }
        }
    )
}

class WKNavigationDelegate : NSObject(), WKNavigationDelegateProtocol {
    override fun webView(
        webView: WKWebView,
        decidePolicyForNavigationAction: WKNavigationAction,
        decisionHandler: (WKNavigationActionPolicy) -> Unit,
    ) {
        println("webView, decisionHandler: $decisionHandler")
        decisionHandler(WKNavigationActionPolicy.WKNavigationActionPolicyAllow)
    }
}
