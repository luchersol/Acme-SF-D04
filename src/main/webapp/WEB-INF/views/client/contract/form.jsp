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

<acme:form>
	<acme:hidden-data path="contractId"/>
	<acme:input-textbox code="client.contract.form.label.code" path="code"/>
	<acme:input-select code="client.contract.form.label.project" path="project" choices="${projects}"/>
	<acme:input-textbox code="client.contract.form.label.instantiationMoment" path="instantiationMoment"/>
	<acme:input-textbox code="client.contract.form.label.providerName" path="providerName"/>
	<acme:input-textbox code="client.contract.form.label.customerName" path="customerName"/>
	<acme:input-textbox code="client.contract.form.label.goal" path="goal"/>
	<acme:input-textbox code="client.contract.form.label.budget" path="budget"/>
	
	<jstl:choose>
	
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:submit code="client.contract.form.button.progressLogs" action="/client/progress-logs/list?masterId=${id}"/>
			<acme:submit code="client.contract.form.button.update" action="/client/contract/update"/>
			<acme:submit code="client.contract.form.button.delete" action="/client/contract/delete"/>
			<acme:submit code="client.contract.form.button.publish" action="/client/contract/publish"/>
		</jstl:when>
		
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="client.contract.form.button.create" action="/client/contract/create"/>
		</jstl:when>
	</jstl:choose>
	
</acme:form>
