
var writer = require('indexing/writer');
var searcher = require('indexing/searcher');
var assertTrue = require('test/assert').assertTrue;

writer.add("index2", "myfile1", "apache lucene", new Date(123));
writer.add("index2", "myfile2", "lucene - the search engine", new Date(234), {"name2":"value2"});
writer.add("index2", "myfile3", "search engine", new Date(345), {"name2":"value2"});

var found = searcher.between("index2", new Date(124), new Date(344));

console.log(JSON.stringify(found));

assertTrue((found !== null) && (found !== undefined) && found.length === 1);