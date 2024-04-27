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
	<acme:message code="client.client-form.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="client.client-form.form.label.completeness-below-25"/>
		</th>
		<td>
			<acme:print value="${completenessBelow25}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.client-form.form.label.completeness-between-25-and-50"/>
		</th>
		<td>
			<acme:print value="${completenessBetween25and50}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.client-form.form.label.completeness-between-50-and-75"/>
		</th>
		<td>
			<acme:print value="${completenessBetween50and75}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.client-form.form.label.completeness-above-75"/>
		</th>
		<td>
			<acme:print value="${completenessAbove75}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.client-form.form.label.average-budget-of-contracts"/>
		</th>
		<td>
			<acme:print value="${averageBudgetOfContracts}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.client-form.form.label.deviation-budget-of-contracts"/>
		</th>
		<td>
			<acme:print value="${deviationBudgetOfContracts}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.client-form.form.label.minimum-budget-of-contracts"/>
		</th>
		<td>
			<acme:print value="${minimumBudgetOfContracts}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.client-form.form.label.maximum-budget-of-contracts"/>
		</th>
		<td>
			<acme:print value="${maximumBudgetOfContracts}"/>
		</td>
	</tr>
</table>

<acme:return/>