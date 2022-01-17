package edu.rutgers.yygh.common.exception;

import edu.rutgers.yygh.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: Shouzhi Fang(frank)
 * @create: 2022-01-16 20:52
 * @description:
 */

@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 全局异常处理类
	 *
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Result error(Exception e){
		e.printStackTrace();
		return Result.fail();
	}

	@ExceptionHandler(YyghException.class)
	@ResponseBody
	public Result yyghException(Exception e){
		e.printStackTrace();
		return Result.fail();
	}
}
