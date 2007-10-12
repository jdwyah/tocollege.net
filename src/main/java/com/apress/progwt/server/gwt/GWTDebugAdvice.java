package com.apress.progwt.server.gwt;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;

import com.apress.progwt.client.exception.BusinessException;

public class GWTDebugAdvice {
	private static final Logger log = Logger.getLogger(GWTDebugAdvice.class);

	/**
	 * Make sure we realize what errors are being sent back to the client
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	public Object wrapGWT(ProceedingJoinPoint pjp) throws Throwable {

		try {
			// start stopwatch
			Object retVal = pjp.proceed();

			return retVal;

		} catch (BusinessException e) {
			log.error("FAILURE: " + e + " " + e.getMessage());
			throw new BusinessException(e);
		} catch (Exception e) {
			log.error("FAILURE: " + e + " " + e.getMessage());
			if (log.isDebugEnabled()) {
				e.printStackTrace();
			}
			throw new BusinessException(e);
		}

	}
}
