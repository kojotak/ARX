/**
 *
 */
package cz.kojotak.arx.ui;

import javax.swing.JPanel;

//import org.lobobrowser.html.gui.HtmlPanel;
//import org.lobobrowser.html.test.SimpleHtmlRendererContext;
//import org.lobobrowser.html.test.SimpleUserAgentContext;

import cz.kojotak.arx.Application;

/**
 * @author TBe
 */
public class ChatPanel extends JPanel {

	private static final long serialVersionUID = 1219944379569201538L;

	public ChatPanel() {
		super();
//		HtmlPanel panel = initSimple();
//		this.add(panel);
	}
/*
	private HtmlPanel initChat() {
		final HtmlPanel panel = new HtmlPanel();
		String uri = "http://www.rotaxmame.cz/php/chat_new.php";

		// A Reader should be created with the correct charset,
		// which may be obtained from the Content-Type header
		// of an HTTP response.
		Reader reader = new StringReader(Application.getInstance().getChatStr());

		// InputSourceImpl constructor with URI recommended
		// so the renderer can resolve page component URLs.
		InputSource is = new InputSourceImpl(reader, uri);

		UserAgentContext ucontext = new LocalUserAgentContext();
		HtmlRendererContext rendererContext = new LocalHtmlRendererContext(
				panel, ucontext);

		// Note: This example does not perform incremental
		// rendering while loading the initial document.
		DocumentBuilderImpl builder = new DocumentBuilderImpl(rendererContext
				.getUserAgentContext(), rendererContext);

		Document document = null;
		try {
			document = builder.parse(is);
		} catch (Exception ex) {
			Application.getInstance().getLogger(this).severe(
					"cannot parser document", ex);
		}

		// Set the document in the HtmlPanel. This method
		// schedules the document to be rendered in the
		// GUI thread.
		panel.setDocument(document, rendererContext);

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Application.getInstance().getLogger(ChatPanel.this).info("fixing prefered size");
				panel.setPreferredSize(new Dimension(800,400));
			}
		});

		return panel;
	}

	private HtmlPanel initSimple() {
		HtmlPanel panel = new HtmlPanel();
		panel.setPreferredWidth(800);
		try {
			new SimpleHtmlRendererContext(panel, new SimpleUserAgentContext())
					.navigate("http://www.rotaxmame.cz/index.php?prihjmeno=coyot&heslo=rotaxheslo&odeslat=Prihlasit");
		} catch (Exception ex) {
			Application.getInstance().getLogger(this).severe("cannot open url",
					ex);
		}
		return panel;
	}

	private static class LocalUserAgentContext extends SimpleUserAgentContext {
		// Override methods from SimpleUserAgentContext to
		// provide more accurate information about application.

		public LocalUserAgentContext() {
		}

		public String getAppMinorVersion() {
			return "0";
		}

		public String getAppName() {
			return "BarebonesTest";
		}

		public String getAppVersion() {
			return "1";
		}

		public String getUserAgent() {
			return "Mozilla/4.0 (compatible;) CobraTest/1.0";
		}
	}

	private static class LocalHtmlRendererContext extends
			SimpleHtmlRendererContext {
		// Override methods from SimpleHtmlRendererContext
		// to provide browser functionality to the renderer.

		public LocalHtmlRendererContext(HtmlPanel contextComponent,
				UserAgentContext ucontext) {
			super(contextComponent, ucontext);
		}

		public void linkClicked(HTMLElement linkNode, URL url, String target) {
			super.linkClicked(linkNode, url, target);
			// This may be removed:
			System.out.println("## Link clicked: " + linkNode);
		}

		public HtmlRendererContext open(URL url, String windowName,
				String windowFeatures, boolean replace) {
			// This is called on window.open().
			HtmlPanel newPanel = new HtmlPanel();
			JFrame frame = new JFrame();
			frame.setSize(600, 400);
			frame.getContentPane().add(newPanel);
			HtmlRendererContext newCtx = new LocalHtmlRendererContext(newPanel,
					this.getUserAgentContext());
			newCtx.navigate(url, "_this");
			return newCtx;
		}
	}*/

}
