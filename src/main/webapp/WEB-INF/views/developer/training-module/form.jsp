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
	<acme:input-textbox code="developer.training-module.form.label.code" path="code"/>
	<jstl:if test="${acme:anyOf(_command, 'show|update|delete|publish')}">
		<acme:input-moment readonly="${true}" code="developer.training-module.form.label.creationMoment" path="creationMoment"/>
		<acme:input-moment readonly="${true}" code="developer.training-module.form.label.updateMoment" path="updateMoment"/>
	</jstl:if>
	<acme:input-textarea code="developer.training-module.form.label.details" path="details"/>
	<acme:input-select code="developer.training-module.form.label.difficultyLevel" path="difficultyLevel" choices="${difficultyLevels}"/>
	<acme:input-url code="developer.training-module.form.label.link" path="link"/>
	<acme:input-double code="developer.training-module.form.label.estimatedTotalTime" path="estimatedTotalTime" placeholder="developer.training-module.form.placeholder.estimatedTotalTime"/>
	<acme:input-select code="developer.training-module.form.label.project" path="project" choices="${projects}"/>

	<jstl:choose>	 
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:button code="developer.training-module.form.button.activities" action="/developer/training-session/list?masterId=${id}"/>			
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:button code="developer.training-module.form.button.activities" action="/developer/training-session/list?masterId=${id}"/>
			<acme:submit code="developer.training-module.form.button.update" action="/developer/training-module/update"/>
			<acme:submit code="developer.training-module.form.button.delete" action="/developer/training-module/delete"/>
			<acme:submit code="developer.training-module.form.button.publish" action="/developer/training-module/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="developer.training-module.form.button.create" action="/developer/training-module/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>


