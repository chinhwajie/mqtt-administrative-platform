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

    dummyData.push({ name, brand, model, category, sn });
  }

  return dummyData;
}

const dummyDevices = generateDummyDataDevices(20);
export {dummyCategories, dummyDevices}
