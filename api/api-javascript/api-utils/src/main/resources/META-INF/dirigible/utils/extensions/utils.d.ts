
declare module "@dirigible/utils" {

    module base64 {
        function encode(text: string): string;

        function decode(text: string): string;
    }


    module hex {
        function encodeBytes(text: string): string;

        function encodeAsNativeBytes(text: string): string;
    }
}