/*
 * Copyright 2008 Jeff Dwyer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
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
