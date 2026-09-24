
export interface Document {
    id:number;
    titile:string;
    description:string;
    uploadedByUsername:string;

}
export interface DocumentRequest {
    titile:string;
    description:string;
}