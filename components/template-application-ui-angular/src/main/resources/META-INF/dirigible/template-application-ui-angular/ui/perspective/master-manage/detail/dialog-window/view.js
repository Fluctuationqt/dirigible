/*
 * Generated by Eclipse Dirigible based on model and template.
 *
 * Do not modify the content as it may be re-generated again.
 */
const viewData = {
    id: "${name}-details",
    label: "${name}",
    link: "/services/web/${projectName}/gen/ui/${perspectiveName}/${masterEntity}/${name}/dialog-window/index.html",
    perspectiveName: "#if($hasReferencedProjection)${referencedProjectionPerspectiveName}#else${perspectiveName}#end"
};

if (typeof exports !== 'undefined') {
    exports.getDialogWindow = function () {
        return viewData;
    }
}