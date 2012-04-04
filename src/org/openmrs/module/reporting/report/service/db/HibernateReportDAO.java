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
package org.openmrs.module.reporting.report.service.db;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.ReportProcessorConfiguration;
import org.openmrs.module.reporting.report.ReportRequest;
import org.openmrs.module.reporting.report.ReportRequest.Status;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.renderer.ReportRenderer;

/**
 * ReportService Database Access Interface
 */
public class HibernateReportDAO implements ReportDAO {
	
	//***** PROPERTIES *****
	private SessionFactory sessionFactory;
	
	//***** INSTANCE METHODS *****
	
	//****** REPORT DESIGNS *****
	
	/**
	 * @param uuid
	 * @return the ReportDesign with the given uuid
	 */
	public ReportDesign getReportDesignByUuid(String uuid) throws DAOException {
		Query q = sessionFactory.getCurrentSession().createQuery("from ReportDesign r where r.uuid = :uuid");
		return (ReportDesign) q.setString("uuid", uuid).uniqueResult();
	}
	
	/**
	 * Get the {@link ReportDesign} with the given id
	 * @param id The Integer ReportDesign id
	 * @return the matching {@link ReportDesign} object
	 * @throws DAOException
	 */
	public ReportDesign getReportDesign(Integer id) throws DAOException {
		return (ReportDesign) sessionFactory.getCurrentSession().get(ReportDesign.class, id);
	}
		
	/**
	 * Return a list of {@link ReportDesign}s for the passed {@link ReportDefinition} and {@link ReportRenderer} class,
	 * optionally including those that are retired
	 * @param includeRetired if true, indicates that retired {@link ReportDesign}s should also be included
	 * @return a List<ReportDesign> object containing all of the {@link ReportDesign}s
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	public List<ReportDesign> getReportDesigns(ReportDefinition reportDefinition, Class<? extends ReportRenderer> rendererType, 
											   boolean includeRetired) throws DAOException {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(ReportDesign.class);
		if (reportDefinition != null) {
			crit.add(Expression.eq("reportDefinition", reportDefinition));
		}
		if (rendererType != null) {
			crit.add(Expression.eq("rendererType", rendererType));
		}
		if (includeRetired == false) {
			crit.add(Expression.eq("retired", false));
		}
		return crit.list();
	}
	
	/**
	 * Save or update the given <code>ReportDesign</code> in the database. If this is a new
	 * ReportDesign, the returned ReportDesign will have a new
	 * {@link ReportDesign#getId()} inserted into it that was generated by the database
	 * 
	 * @param reportDesign The <code>ReportDesign</code> to save or update
	 * @throws DAOException
	 */
	public ReportDesign saveReportDesign(ReportDesign reportDesign) throws DAOException {
		sessionFactory.getCurrentSession().saveOrUpdate(reportDesign);
		return reportDesign;
	}
	
	/**
	 * Purges a <code>ReportDesign</code> from the database.
	 * @param reportDesign The <code>ReportDesign</code> to remove from the system
	 * @throws DAOException
	 */
	public void purgeReportDesign(ReportDesign reportDesign) {
		sessionFactory.getCurrentSession().delete(reportDesign);
	}
	
	//****** REPORT PROCESSOR CONFIGURATIONS *****
	
	/**
	 * Saves a {@link ReportProcessorConfiguration} to the database and returns it
	 */
	public ReportProcessorConfiguration saveReportProcessorConfiguration(ReportProcessorConfiguration processorConfiguration) {
		sessionFactory.getCurrentSession().saveOrUpdate(processorConfiguration);
		return processorConfiguration;
	}

	/**
	 * @return the {@link ReportProcessorConfiguration} with the passed id
	 */
	public ReportProcessorConfiguration getReportProcessorConfiguration(Integer id) {
		return (ReportProcessorConfiguration) sessionFactory.getCurrentSession().get(ReportProcessorConfiguration.class, id);
	}

	/**
	 * @return the {@link ReportProcessorConfiguration} with the passed uuid
	 */
	public ReportProcessorConfiguration getReportProcessorConfigurationByUuid(String uuid) {
		Query q = sessionFactory.getCurrentSession().createQuery("from ReportProcessorConfiguration r where r.uuid = :uuid");
		return (ReportProcessorConfiguration) q.setString("uuid", uuid).uniqueResult();
	}
	
	/**
	 * @return all the {@link ReportProcessorConfiguration}s
	 */
	@SuppressWarnings("unchecked")
	public List<ReportProcessorConfiguration> getAllReportProcessorConfigurations(boolean includeRetired) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(ReportProcessorConfiguration.class);
		if (includeRetired == false) {
			crit.add(Expression.eq("retired", false));
		}
		return crit.list();
	}
	
	/**
	 * Deletes the passed {@link ReportProcessorConfiguration}
	 */
	public void purgeReportProcessorConfiguration(ReportProcessorConfiguration processorConfiguration) {
		sessionFactory.getCurrentSession().delete(processorConfiguration);
	}
	
	//****** REPORT REQUESTS *****
	
	/**
	 * @see ReportDAO#saveReportRequest(ReportRequest)
	 */
	public ReportRequest saveReportRequest(ReportRequest request) {
		sessionFactory.getCurrentSession().saveOrUpdate(request);
		return request;
	}

	/**
	 * @see ReportDAO#getReportRequest(java.lang.Integer)
	 */
	public ReportRequest getReportRequest(Integer id) {
		return (ReportRequest) sessionFactory.getCurrentSession().get(ReportRequest.class, id);
	}

	/**
	 * @see ReportDAO#getReportRequestByUuid(java.lang.String)
	 */
	public ReportRequest getReportRequestByUuid(String uuid) {
		Query q = sessionFactory.getCurrentSession().createQuery("from ReportRequest r where r.uuid = :uuid");
		return (ReportRequest) q.setString("uuid", uuid).uniqueResult();
	}

	/**
	 * @see ReportDAO#getReportRequests(definition.ReportDefinition, Date, Date, Status)
	 */
	@SuppressWarnings("unchecked")
	public List<ReportRequest> getReportRequests(ReportDefinition reportDefinition, Date requestOnOrAfter, Date requestOnOrBefore, Status...statuses) {
		Criteria c = sessionFactory.getCurrentSession().createCriteria(ReportRequest.class);
		if (reportDefinition != null) {
			c.add(Restrictions.eq("reportDefinition.definition", reportDefinition.getUuid()));
		}
		if (requestOnOrAfter != null) {
			c.add(Restrictions.ge("requestDate", requestOnOrAfter));
		}
		if (requestOnOrBefore != null) {
			c.add(Restrictions.le("requestDate", requestOnOrBefore));
		}
		if (statuses != null && statuses.length > 0) {
			c.add(Restrictions.in("status", statuses));
		}
		return c.list();
	}
	
	/**
	 * @see ReportDAO#purgeReportRequest(ReportRequest)
	 */
	public void purgeReportRequest(ReportRequest request) {
		sessionFactory.getCurrentSession().delete(request);
	}
	
	//***** PROPERTY ACCESS *****

	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * @param sessionFactory the sessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
