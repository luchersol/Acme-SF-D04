<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<h2>
	<acme:message code="auditor.dashboard.form.title"/>
</h2>

<table class="table table-sm">
	
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.totalStaticCodeAudits"/>
		</th>
		<td>
			<acme:print value="${totalStaticCodeAudits}"/>
		</td>
	</tr>
	
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.totalDynamicCodeAudits"/>
		</th>
		<td>
			<acme:print value="${totalDynamicCodeAudits}"/>
		</td>
	</tr>
	
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.averageAuditRecordsInCodeAudits"/>
		</th>
		<td>
			<acme:print value="${averageAuditRecordsInCodeAudits}"/>
		</td>
	</tr>
	
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.deviationAuditRecordsInCodeAudits"/>
		</th>
		<td>
			<acme:print value="${deviationAuditRecordsInCodeAudits}"/>
		</td>
	</tr>

	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.minimumAuditRecordsInCodeAudits"/>
		</th>
		<td>
			<acme:print value="${minimumAuditRecordsInCodeAudits}"/>
		</td>
	</tr>

	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.maximumAuditRecordsInCodeAudits"/>
		</th>
		<td>
			<acme:print value="${maximumAuditRecordsInCodeAudits}"/>
		</td>
	</tr>
	
	
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.averagePeriodOfAuditRecordsInCodeAudits"/>
		</th>
		<td>
			<acme:print value="${averagePeriodOfAuditRecordsInCodeAudits == Double.NaN ? '' : averagePeriodOfAuditRecordsInCodeAudits}"/>
		</td>
	</tr>
	
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.deviationPeriodOfAuditRecordsInCodeAudits"/>
		</th>
		<td>
			<acme:print value="${deviationPeriodOfAuditRecordsInCodeAudits == Double.NaN ? '' : deviationPeriodOfAuditRecordsInCodeAudits }"/>
		</td>
	</tr>

	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.minimumPeriodOfAuditRecordsInCodeAudits"/>
		</th>
		<td>
			<acme:print value="${minimumPeriodOfAuditRecordsInCodeAudits == Double.NaN ? '' : minimumPeriodOfAuditRecordsInCodeAudits }"/>
		</td>
	</tr>
	
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.maximumPeriodOfAuditRecordsInCodeAudits"/>
		</th>
		<td>
			<acme:print value="${maximumPeriodOfAuditRecordsInCodeAudits == Double.NaN ? '' : maximumPeriodOfAuditRecordsInCodeAudits}"/>
		</td>
	</tr>

</table>

<acme:return/>

