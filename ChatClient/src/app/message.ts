export class Message {

    constructor( 
                public typ   : string,
                public sender : string,
                public group  : string,
                public payload: string,
                public value:   string[]


    ) {}
}