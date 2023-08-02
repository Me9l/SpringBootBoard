package com.example.SpringBootBoard;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;

@Component	// Bean 객체 등록 ( commonUtil 로 객체 생성 된다. )
public class CommonUtil {
	// @Component , @Controller , @Service , @Repository 등등 ( Bean에 객체 등록 )
	public String markdown( String markdown ) {
		Parser parser = Parser.builder().build();
		Node document = parser.parse(markdown);
		HtmlRenderer render = HtmlRenderer.builder().build();
		
		return render.render(document);
	}
}
