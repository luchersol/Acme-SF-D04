<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form> 
	<acme:input-textbox code="administrator.system-configuration.form.label.systemCurrency" path="systemCurrency"/>
	<acme:input-moment code="administrator.system-configuration.form.label.acceptedCurrencies" path="acceptedCurrencies"/>
	<jstl:choose>	 
		<jstl:when test="${_command == 'show'}">
			<acme:submit code="administrator.system-configuration.form.button.update" action="/administrator/system-configuration/update"/>
		</jstl:when>	
	</jstl:choose>
</acme:form>

