package com.ds.mcp_qty_item;

import java.util.List;

import com.ds.mcp_qty_item.service.QtyItemService;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean; // Needed to expose the tools

@SpringBootApplication
public class McpQtyItemApplication {

	public static void main(String[] args) {
		SpringApplication.run(McpQtyItemApplication.class, args);
	}

	@Bean
	public List<ToolCallback> tools(QtyItemService qtyItemService) {
		return List.of(ToolCallbacks.from(qtyItemService));
	}

}
