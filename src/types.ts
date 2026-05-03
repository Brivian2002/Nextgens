/**
 * @license
 * SPDX-License-Identifier: Apache-2.0
 */

export type Screen = 'dashboard' | 'tools' | 'code' | 'settings';

export interface Tool {
  id: string;
  name: string;
  description: string;
  icon: string;
}

export interface NetworkStats {
  timestamp: string;
  download: number;
  upload: number;
}
