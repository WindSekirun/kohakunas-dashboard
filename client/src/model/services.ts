export class ServiceCategory {
    name: string = "";
    services: Service[] = [];
}

export class Service {
    title: string = "";
    desc: string = "";
    connectUrl: string = "";
    role: string = "";
    icon: string = "";
    category: string = "";
    intranetService: boolean = false;
}