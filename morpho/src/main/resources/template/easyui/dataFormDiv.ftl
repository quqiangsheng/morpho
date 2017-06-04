<!-- ${formName}  div风格的Form -->
﻿<form id="${formName}" name="${formName}" method="post" action="">   
	<#list itemMap?keys as mykey>
	 <div>   
        <label for="${mykey}">${itemMap[mykey]}</label>   
        <input class="easyui-validatebox" type="text" name="${mykey}" id="${mykey}Id" data-options="" />   
     </div> 					
	</#list>
</form>
