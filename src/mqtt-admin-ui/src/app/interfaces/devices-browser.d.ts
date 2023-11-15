import { Category } from "./device-category";

export interface DevicesBrowserQuery {
    iotCategory: Category[],
    typeValue: {
        type: String,
        value: String
    },
    status: String,
    topic: {
        checked: Boolean,
        value: String
    },
    dateRange: {
        start: Date,
        end: Date
    }
}