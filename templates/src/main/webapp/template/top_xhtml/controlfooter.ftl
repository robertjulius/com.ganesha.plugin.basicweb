${parameters.after?if_exists}<#t/>
    </td><#lt/>
</tr>

<#--
	Only show message if errors are available.
	This will be done if ActionSupport is used.
-->
<#assign hasFieldErrors = parameters.name?? && fieldErrors?? && fieldErrors[parameters.name]??/>
<#if hasFieldErrors>
<#list fieldErrors[parameters.name] as error>
<tr errorFor="${parameters.id}">
    <td align="left" valign="top"><span class="errorMessage">${error?html}</span></td>
</tr>
</#list>
</#if>
<tr>
    <td><br/></td>
</tr>