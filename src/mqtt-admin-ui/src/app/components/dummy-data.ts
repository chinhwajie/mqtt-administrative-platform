import {Category} from "../interfaces/device-category";

const dummyCategories: Category[] = [
  {id: 1, name: 'Sensor'},
  {id: 2, name: 'Mob'},
  {id: 3, name: 'Lithium'},
  {id: 4, name: 'Beryllium'},
  {id: 5, name: 'Boron'},
  {id: 6, name: 'Carbon'},
];

function generateDummyDataDevices(count: number) {
  const dummyData = [];

  for (let i = 0; i < count; i++) {
    const name = `Name ${i + 1}`;
    const brand = `Brand ${i + 1}`;
    const model = `Model ${i + 1}`;
    const category = `Cat ${i + 1}`;
    const sn = Math.floor(Math.random() * 1000000000000).toString(); // Generating a random serial number

    dummyData.push({name, brand, model, category, sn});
  }
  return dummyData;
}

function generateSubscribedTopics(count: number) {
  const dummyData = [];
  for (let i = 0; i < count; i++) {
    dummyData.push(
      {
        name: "topic " + Math.floor(Math.random() * 1000).toString(),
        connect: Math.floor((Math.random() * 1000) % 2) == 1
      }
    );
  }
  return dummyData;
}

function generateMessages(count: number) {
  const dummyData = [];
  for (let i = 0; i < count; i++) {
    dummyData.push(
      {
        deviceId: i,
        deviceName: "Device " + i,
        topic: "Topic " + Math.floor(Math.random() * 1000).toString(),
        message: "{message: ...}",
        timestamp: new Date().toLocaleDateString('en-US', {
          year: 'numeric',
          month: 'short',
          day: 'numeric',
          hour: '2-digit',
          minute: '2-digit',
          second: '2-digit'
        })
      }
    )
  }
  return dummyData;
}

const dummyDevices = generateDummyDataDevices(20);
const dummyTopics = generateSubscribedTopics(5);
const dummyMessages = generateMessages(30);

export {dummyCategories, dummyDevices, dummyTopics, generateSubscribedTopics, dummyMessages}
