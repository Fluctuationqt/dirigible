declare module "@dirigible/utils" {
    module base64 {
        function encode(text: string):string;
        function encodeAsBytes(input:string):string;
        function decode(text: string):string;
        function  encodeAsNativeBytes(text:string):string;
        function decodeAsNativeBytes(text:string):string;
    }

    module alphanumeric {
        function toAlphanumeric(string:string);
        function  randomstring(length:number,charset:string):string;
        function alphanumeric(length:number,lowercase:boolean):string;
        function alpha(length:number,lowercase:boolean):string;
        function numeric(length:number):string;
        function isNumeric(str:string):boolean;
        function isAlphanumeric(str:string):boolean;
    }

    module assert {
        function assertTrue(condition:boolean,message:string);
        function assertNotNull(condition:boolean,message:string);
        function assertEquals(condition:boolean,message:string);
    }

    module digest {
        function md5AsNativeBytes(input:any):string;
        function md5Hex(input:any):string;
        function sha1(input:any):string;
        function sha1AsNativeBytes(input:any):string;
        function sha256(input:any):string;
        function sha256AsNativeBytes(input:any):string;
        function sha384(input:any):string;
        function sha384AsNativeBytes(input:string):string;
        function sha512(input:any):string;
        function sha512AsNativeBytes(input:any):string;
        function sha1Hex(input:any):string;
    }
}