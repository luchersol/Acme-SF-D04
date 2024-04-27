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
	<acme:message code="administrator.administrator-form.form.title.general-indicators"/>
</h2>

<table class="table table-sm">

	<tr>
		<th scope="row">
			<acme:message code="administrator.administrator-form.form.label.total-number-administrator"/>
		</th>
		<td>
			<acme:print value="${totalNumberAdministrator}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.administrator-form.form.label.total-number-auditor"/>
		</th>
		<td>
			<acme:print value="${totalNumberAuditor}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.administrator-form.form.label.total-number-client"/>
		</th>
		<td>
			<acme:print value="${totalNumberClient}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="administrator.administrator-form.form.label.total-number-developer"/>
		</th>
		<td>
			<acme:print value="${totalNumberDeveloper}"/>
		</td>
	</tr>
	
	<tr>
		<th scope="row">
			<acme:message code="administrator.administrator-form.form.label.total-number-manager"/>
		</th>
		<td>
			<acme:print value="${totalNumberManager}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.administrator-form.form.label.total-number-sponsor"/>
		</th>
		<td>
			<acme:print value="${totalNumberSponsor}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.administrator-form.form.label.ratio-notices-with-email-and-link"/>
		</th>
		<td>
			<acme:print value="${ratioNoticesWithEmailAndLink}"/>
		</td>
	</tr>

	<tr>
		<th scope="row">
			<acme:message code="administrator.administrator-form.form.label.ratio-critical-objetives"/>
		</th>
		<td>
			<acme:print value="${ratioCriticalObjetives}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.administrator-form.form.label.ratio-not-critical-objetives"/>
		</th>
		<td>
			<acme:print value="${ratioNotCriticalObjetives}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.administrator-form.form.label.average-value-risk"/>
		</th>
		<td>
			<acme:print value="${averageValueRisk}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="administrator.administrator-form.form.label.deviation-value-risk"/>
		</th>
		<td>
			<acme:print value="${deviationValueRisk}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="administrator.administrator-form.form.label.minimum-value-risk"/>
		</th>
		<td>
			<acme:print value="${minimumValueRisk}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="administrator.administrator-form.form.label.maximum-value-risk"/>
		</th>
		<td>
			<acme:print value="${maximumValueRisk}"/>
		</td>
	</tr>	
	
</table>