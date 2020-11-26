/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.reporting.cohort.definition;

import org.openmrs.module.reporting.common.Localized;
import org.openmrs.module.reporting.definition.configuration.ConfigurationProperty;
import org.openmrs.module.reporting.definition.configuration.ConfigurationPropertyCachingStrategy;
import org.openmrs.module.reporting.evaluation.caching.Caching;

@Caching(strategy= ConfigurationPropertyCachingStrategy.class)
@Localized("reporting.CadreCohortDefinition")
public class CadreCohortDefinition extends BaseCohortDefinition {

    public static final long serialVersionUID = 1L;

	//***** PROPERTIES *****

	@ConfigurationProperty(group="cadres")
	private Boolean troupeIncluded = Boolean.FALSE;

	@ConfigurationProperty(group="cadres")
	private Boolean civilianIncluded = Boolean.FALSE;

	//***** CONSTRUCTORS *****

	/**
	 * Default constructor
	 */
	public CadreCohortDefinition() {
		super();
	}

	//***** INSTANCE METHODS *****

	/**
	 * @see Object#toString()
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();		
		if (isTroupeIncluded() == Boolean.TRUE) {
			buffer.append("Troupe");
		}
		if (isCivilianIncluded() == Boolean.TRUE) {
			buffer.append((buffer.length() > 0 ? "," : "") + "Civilian");
		}
		if (buffer.length() == 0) {
			return "No Patients";
		}
		return buffer.toString();
	}

	//***** PROPERTY ACCESS *****

	/**
	 * @return the troupeIncluded
	 */
	public Boolean isTroupeIncluded() {
		return troupeIncluded;
	}

	/**
	 * @return the civilianIncluded
	 */
	public Boolean isCivilianIncluded() {
		return civilianIncluded;
	}

	public Boolean getTroupeIncluded() {
		return troupeIncluded;
	}

	public void setTroupeIncluded(Boolean troupeIncluded) {
		this.troupeIncluded = troupeIncluded;
	}

	public Boolean getCivilianIncluded() {
		return civilianIncluded;
	}

	public void setCivilianIncluded(Boolean civilianIncluded) {
		this.civilianIncluded = civilianIncluded;
	}
}
