// JavaScript with imports
import { readFile } from 'fs';
import path from 'path';
import * as utils from './utils';

export function processFile(filename) {
  const fullPath = path.join(__dirname, filename);
  return readFile(fullPath, 'utf-8');
}

export default {
  name: 'FileProcessor',
  version: '1.0.0'
};

