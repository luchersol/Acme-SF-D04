<%--
- form.jsp
-
- Copyright (C) 2012-2024 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<h2>
	<acme:message code="manager.manager-form.form.title.general-indicators"/>
</h2>

<table class="table table-sm">

	<tr>
		<th scope="row">
			<acme:message code="manager.manager-form.form.label.total-number-project-must"/>
		</th>
		<td>
			<acme:print value="${totalNumberProjectMust}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.manager-form.form.label.total-number-project-should"/>
		</th>
		<td>
			<acme:print value="${totalNumberProjectShould}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.manager-form.form.label.total-number-project-could"/>
		</th>
		<td>
			<acme:print value="${totalNumberProjectCould}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="manager.manager-form.form.label.total-number-project-wont"/>
		</th>
		<td>
			<acme:print value="${totalNumberProjectWont}"/>
		</td>
	</tr>
	
	<tr>
		<th scope="row">
			<acme:message code="manager.manager-form.form.label.average-estimated-cost-user-stories"/>
		</th>
		<td>
			<acme:print value="${averageEstimatedCostUserStories}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.manager-form.form.label.deviation-estimated-cost-user-stories"/>
		</th>
		<td>
			<acme:print value="${deviationEstimatedCostUserStories}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.manager-form.form.label.minimum-estimated-cost-user-stories"/>
		</th>
		<td>
			<acme:print value="${minimumEstimatedCostUserStories}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="manager.manager-form.form.label.maximum-estimated-cost-user-stories"/>
		</th>
		<td>
			<acme:print value="${maximumEstimatedCostUserStories}"/>
		</td>
	</tr>
	
	<tr>
		<th scope="row">
			<acme:message code="manager.manager-form.form.label.average-estimated-cost-projects"/>
		</th>
		<td>
			<acme:print value="${averageEstimatedCostProjects}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.manager-form.form.label.deviation-estimated-cost-projects"/>
		</th>
		<td>
			<acme:print value="${deviationEstimatedCostProjects}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.manager-form.form.label.minimum-estimated-cost-projects"/>
		</th>
		<td>
			<acme:print value="${minimumEstimatedCostProjects}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="manager.manager-form.form.label.maximum-estimated-cost-projects"/>
		</th>
		<td>
			<acme:print value="${maximumEstimatedCostProjects}"/>
		</td>
	</tr>
	
</table>
