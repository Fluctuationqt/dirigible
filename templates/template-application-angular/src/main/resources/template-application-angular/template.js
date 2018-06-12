exports.getTemplate = function() {
	return {
		'name': 'Full-stack Application (AngularJS)',
		'description': 'Full-stack Application with a Database Schema, a set of REST Services and an AngularJS User Interfaces',
		'model':'true',
		'sources': [
		{
			'_section': 'API',
			'location': '/template-application-angular/api/http.js.template', 
			'action': 'copy',
			'rename': 'api/http.js',
		}, {
			'_section': 'API',
			'location': '/template-application-angular/api/entity.js.template', 
			'action': 'generate',
			'rename': 'api/{{fileName}}.js',
			'collection': 'models',
			'engine': 'velocity'
		},



		{
			'_section': 'Data',
			'location': '/template-application-angular/data/application.schema.template', 
			'action': 'generate',
			'rename': 'data/{{fileNameBase}}.schema'
		}, {
			'_section': 'Data',
			'location': '/template-application-angular/data/dao/entity.js.template', 
			'action': 'generate',
			'rename': 'data/dao/{{fileName}}.js',
			'collection': 'models',
			'engine': 'velocity'
		},



		{
			'_section': 'Extensions',
			'location': '/template-application-angular/extensions/perspective/perspective.extension.template', 
			'action': 'generate',
			'rename': 'extensions/perspective/perspective.extension'
		}, {
			'_section': 'Extensions',
			'location': '/template-application-angular/extensions/perspective/perspective.js.template', 
			'action': 'generate',
			'rename': 'extensions/perspective/perspective.js',
			'engine': 'velocity'
		}, {
			'_section': 'Extensions',
			'location': '/template-application-angular/extensions/tiles/tiles.extension.template', 
			'action': 'generate',
			'rename': 'extensions/tiles/tiles.extension'
		}, {
			'_section': 'Extensions',
			'location': '/template-application-angular/extensions/tiles/tiles.js.template', 
			'action': 'generate',
			'rename': 'extensions/tiles/tiles.js',
			'engine': 'velocity'
		}, {
			'_section': 'Extensions',
			'location': '/template-application-angular/extensions/menu.extensionpoint.template', 
			'action': 'generate',
			'rename': 'extensions/menu.extensionpoint'
		}, {
			'_section': 'Extensions',
			'location': '/template-application-angular/extensions/views/manage/view-manage.extensionpoint.template', 
			'action': 'generate',
			'rename': 'extensions/views/view-manage.extensionpoint'
		}, {
			'_section': 'Extensions',
			'location': '/template-application-angular/extensions/views/list/entity.extension.template', 
			'action': 'generate',
			'rename': 'extensions/views/{{fileName}}.extension',
			'collection': 'uiListModels'
		}, {
			'_section': 'Extensions',
			'location': '/template-application-angular/extensions/views/manage/entity.extension.template', 
			'action': 'generate',
			'rename': 'extensions/views/{{fileName}}.extension',
			'collection': 'uiManageModels'
		}, {
			'_section': 'Extensions',
			'location': '/template-application-angular/extensions/views/master-list/entity-view.extensionpoint.template', 
			'action': 'generate',
			'rename': 'extensions/views/{{fileName}}-view.extensionpoint',
			'collection': 'uiListMasterModels'
		}, {
			'_section': 'Extensions',
			'location': '/template-application-angular/extensions/views/master-list/entity-view-master.extension.template', 
			'action': 'generate',
			'rename': 'extensions/views/{{fileName}}-view-master.extension',
			'collection': 'uiListMasterModels'
		}, {
			'_section': 'Extensions',
			'location': '/template-application-angular/extensions/views/master-list/entity-view-detail.extension.template', 
			'action': 'generate',
			'rename': 'extensions/views/{{fileName}}-view-detail.extension',
			'collection': 'uiListDetailsModels',
			'engine': 'velocity'
		}, {
			'_section': 'Extensions',
			'location': '/template-application-angular/extensions/views/master-list/entity.extension.template', 
			'action': 'generate',
			'rename': 'extensions/views/{{fileName}}.extension',
			'collection': 'uiListMasterModels'
		}, {
			'_section': 'Extensions',
			'location': '/template-application-angular/extensions/views/master-manage/entity-view.extensionpoint.template', 
			'action': 'generate',
			'rename': 'extensions/views/{{fileName}}-view.extensionpoint',
			'collection': 'uiManageMasterModels'
		}, {
			'_section': 'Extensions',
			'location': '/template-application-angular/extensions/views/master-manage/entity-view-master.extension.template', 
			'action': 'generate',
			'rename': 'extensions/views/{{fileName}}-view-master.extension',
			'collection': 'uiManageMasterModels'
		}, {
			'_section': 'Extensions',
			'location': '/template-application-angular/extensions/views/master-manage/entity-view-detail.extension.template', 
			'action': 'generate',
			'rename': 'extensions/views/{{fileName}}-view-detail.extension',
			'collection': 'uiManageDetailsModels',
			'engine': 'velocity'
		}, {
			'_section': 'Extensions',
			'location': '/template-application-angular/extensions/views/master-manage/entity.extension.template', 
			'action': 'generate',
			'rename': 'extensions/views/{{fileName}}.extension',
			'collection': 'uiManageMasterModels'
		},



		{
			'_section': 'UI - Manage Models',
			'location': '/template-application-angular/views/manage/index.html.template', 
			'action': 'generate',
			'rename': 'views/{{fileName}}/index.html',
			'collection': 'uiManageModels',
			'engine': 'velocity'
		}, {
			'_section': 'UI - Manage Models',
			'location': '/template-application-angular/views/manage/controller.js.template', 
			'action': 'generate',
			'rename': 'views/{{fileName}}/controller.js',
			'collection': 'uiManageModels',
			'engine': 'velocity'
		}, {
			'_section': 'UI - Manage Models',
			'location': '/template-application-angular/views/manage/view.js.template', 
			'action': 'generate',
			'collection': 'uiManageModels',
			'rename': 'views/{{fileName}}/view.js'
		}, {
			'_section': 'UI - Manage Models',
			'location': '/template-application-angular/views/manage/menu/item.extension.template', 
			'action': 'generate',
			'rename': 'views/{{fileName}}/menu/item.extension',
			'collection': 'uiManageModels'
		}, {
			'_section': 'UI - Manage Models',
			'location': '/template-application-angular/views/manage/menu/item.js.template', 
			'action': 'generate',
			'rename': 'views/{{fileName}}/menu/item.js',
			'collection': 'uiManageModels'
		},



		{
			'_section': 'UI - List Models',
			'location': '/template-application-angular/views/list/index.html.template', 
			'action': 'generate',
			'rename': 'views/{{fileName}}/index.html',
			'collection': 'uiListModels',
			'engine': 'velocity'
		}, {
			'_section': 'UI - List Models',
			'location': '/template-application-angular/views/list/controller.js.template', 
			'action': 'generate',
			'rename': 'views/{{fileName}}/controller.js',
			'collection': 'uiListModels',
			'engine': 'velocity'
		}, {
			'_section': 'UI - List Models',
			'location': '/template-application-angular/views/list/view.js.template', 
			'action': 'generate',
			'collection': 'uiListModels',
			'rename': 'views/{{fileName}}/view.js'
		}, {
			'_section': 'UI - List Models',
			'location': '/template-application-angular/views/list/menu/item.extension.template', 
			'action': 'generate',
			'rename': 'views/{{fileName}}/menu/item.extension',
			'collection': 'uiListModels'
		}, {
			'_section': 'UI - List Models',
			'location': '/template-application-angular/views/list/menu/item.js.template', 
			'action': 'generate',
			'rename': 'views/{{fileName}}/menu/item.js',
			'collection': 'uiListModels'
		},



		{
			'_section': 'UI - List Master Models',
			'location': '/template-application-angular/views/master-list/index.html.template', 
			'action': 'generate',
			'rename': 'views/master/{{fileName}}/index.html',
			'collection': 'uiListMasterModels',
		}, {
			'_section': 'UI - List Master Models',
			'location': '/template-application-angular/views/master-list/view.js.template', 
			'action': 'generate',
			'rename': 'views/master/{{fileName}}/view.js',
			'collection': 'uiListMasterModels'
		}, {
			'_section': 'UI - List Master Models',
			'location': '/template-application-angular/views/master-list/master/index.html.template', 
			'action': 'generate',
			'rename': 'views/master/{{fileName}}/master/index.html',
			'collection': 'uiListMasterModels',
			'engine': 'velocity'
		}, {
			'_section': 'UI - List Master Models',
			'location': '/template-application-angular/views/master-list/master/controller.js.template', 
			'action': 'generate',
			'rename': 'views/master/{{fileName}}/master/controller.js',
			'collection': 'uiListMasterModels',
			'engine': 'velocity'
		}, {
			'_section': 'UI - List Master Models',
			'location': '/template-application-angular/views/master-list/master/view.js.template', 
			'action': 'generate',
			'rename': 'views/master/{{fileName}}/master/view.js',
			'collection': 'uiListMasterModels'
		},



		{
			'_section': 'UI - List Details Models',
			'location': '/template-application-angular/views/master-list/details/index.html.template', 
			'action': 'generate',
			'rename': 'views/master/details/{{fileName}}/index.html',
			'collection': 'uiListDetailsModels',
			'engine': 'velocity'
		}, {
			'_section': 'UI - List Details Models',
			'location': '/template-application-angular/views/master-list/details/controller.js.template', 
			'action': 'generate',
			'rename': 'views/master/details/{{fileName}}/controller.js',
			'collection': 'uiListDetailsModels',
			'engine': 'velocity'
		}, {
			'_section': 'UI - List Details Models',
			'location': '/template-application-angular/views/master-list/details/view.js.template', 
			'action': 'generate',
			'rename': 'views/master/details/{{fileName}}/view.js',
			'collection': 'uiListDetailsModels'
		},



		{
			'_section': 'UI - Manage Master Models',
			'location': '/template-application-angular/views/master-manage/index.html.template', 
			'action': 'generate',
			'rename': 'views/master/{{fileName}}/index.html',
			'collection': 'uiManageMasterModels',
		}, {
			'_section': 'UI - Manage Master Models',
			'location': '/template-application-angular/views/master-manage/view.js.template', 
			'action': 'generate',
			'rename': 'views/master/{{fileName}}/view.js',
			'collection': 'uiManageMasterModels'
		}, {
			'_section': 'UI - Manage Master Models',
			'location': '/template-application-angular/views/master-manage/master/index.html.template', 
			'action': 'generate',
			'rename': 'views/master/{{fileName}}/master/index.html',
			'collection': 'uiManageMasterModels',
			'engine': 'velocity'
		}, {
			'_section': 'UI - Manage Master Models',
			'location': '/template-application-angular/views/master-manage/master/controller.js.template', 
			'action': 'generate',
			'rename': 'views/master/{{fileName}}/master/controller.js',
			'collection': 'uiManageMasterModels',
			'engine': 'velocity'
		}, {
			'_section': 'UI - Manage Master Models',
			'location': '/template-application-angular/views/master-manage/master/view.js.template', 
			'action': 'generate',
			'rename': 'views/master/{{fileName}}/master/view.js',
			'collection': 'uiManageMasterModels'
		},



		{
			'_section': 'UI - Manage Details Models',
			'location': '/template-application-angular/views/master-manage/details/index.html.template', 
			'action': 'generate',
			'rename': 'views/master/details/{{fileName}}/index.html',
			'collection': 'uiManageDetailsModels',
			'engine': 'velocity'
		}, {
			'_section': 'UI - Manage Details Models',
			'location': '/template-application-angular/views/master-manage/details/controller.js.template', 
			'action': 'generate',
			'rename': 'views/master/details/{{fileName}}/controller.js',
			'collection': 'uiManageDetailsModels',
			'engine': 'velocity'
		}, {
			'_section': 'UI - Manage Details Models',
			'location': '/template-application-angular/views/master-manage/details/view.js.template', 
			'action': 'generate',
			'rename': 'views/master/details/{{fileName}}/view.js',
			'collection': 'uiManageDetailsModels'
		},



		{
			'_section': 'UI - Index.html',
			'location': '/template-application-angular/index.html.template', 
			'action': 'generate',
			'rename': 'index.html'
		}],
		'parameters': [{
			'name': 'extensionName',
			'label': 'Extension Name'
		}, {
			'name': 'launchpadName',
			'label': 'Launchpad Name'
		}, {
			'name': 'brand',
			'label': 'Brand'
		}, {
			'name': 'moduleName',
			'label': 'Module Name'
		}, {
			'name': 'moduleIcon',
			'label': 'Module Icon'
		}, {
			'name': 'moduleOrder',
			'label': 'Module Order'
		}]
	};
};