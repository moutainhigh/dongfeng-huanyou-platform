package com.navinfo.dongfeng.terminal.comm.init;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.CommandCache;
import com.navinfo.dongfeng.terminal.comm.common.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandInitialize {
	Logger logger = LoggerFactory.getLogger(CommandInitialize.class);
	@Autowired
	public CommandInitialize(List<Command> commandList) {
		for (Command command : commandList) {
			Class<? extends Command> clazz = command.getClass();
			String cmd = clazz.getSimpleName();
			String[] cmdinfo = StringUtils.split(clazz.getSimpleName(), "_", true);
			CommandCache.getInstance().addCommand(cmdinfo[0]+"_"+cmdinfo[1],
                    command);
		}

		logger.info("加载协议包完成.");
	}
}
