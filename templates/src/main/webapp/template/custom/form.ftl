<#include "/${parameters.templateDir}/simple/form-common.ftl" />
<#if parameters.onreset??>
 onreset="${parameters.onreset?html}"<#rt/>
</#if>
>
<#--
	Only show message if errors are available.
	This will be done if ActionSupport is used.
-->
<#--
<#assign hasActionErrors = !actionErrors.isEmpty() />
<#if hasActionErrors>
<div class="errorMessage">
<#list actionErrors as error>
	<label id="errorMessage">${error?html}</label><br/>
</#list>
</div>
</#if>
-->