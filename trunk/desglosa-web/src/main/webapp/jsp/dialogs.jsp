<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page   language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<sj:dialog id="errorDialog" 
       buttons="{'OK':function() { $('#errorDialog').dialog('close'); }}"
      	   autoOpen="false"
      	   modal="true"
      	   closeOnEscape="true"
      	   showEffect="fold"
      	   hideEffect="scale"
      	   draggable="false"
      	   title="%{getText('label.dialog.title.error')}">
     	<div id="errorDialogBody"></div>
  </sj:dialog>
  <sj:dialog id="infoDialog" 
       buttons="{'OK':function() { $('#infoDialog').dialog('close'); }}"
      	   autoOpen="false"
      	   modal="true"
      	   closeOnEscape="true"
      	   showEffect="fold"
      	   hideEffect="scale"
      	   draggable="false"
      	   title="%{getText('label.dialog.title.info')}">
     	<div id="infoDialogBody"></div>
  </sj:dialog>
  <sj:dialog id="warningDialog" 
       buttons="{'OK':function() { $('#warningDialog').dialog('close'); }}"
      	   autoOpen="false"
      	   modal="true"
      	   closeOnEscape="true"
      	   showEffect="fold"
      	   hideEffect="scale"
      	   draggable="false"
      	   title="%{getText('label.dialog.title.warning')}">
     	<div id="warningDialogBody"></div>
</sj:dialog>