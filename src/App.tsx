/**
 * @license
 * SPDX-License-Identifier: Apache-2.0
 */

import React, { useState, useEffect } from 'react';
import { motion, AnimatePresence } from 'motion/react';
import { ICON_MAP } from './constants';
import { Screen } from './types';
import Dashboard from './components/Dashboard';
import Tools from './components/Tools';
import CodeExecutor from './components/CodeExecutor';
import Settings from './components/Settings';
import { cn } from './lib/utils';

export default function App() {
  const [activeTab, setActiveTab] = useState<Screen>('dashboard');
  const [isVpnConnected, setIsVpnConnected] = useState(false);

  const navItems = [
    { id: 'dashboard', label: 'Dashboard', icon: 'Home' },
    { id: 'tools', label: 'Tools', icon: 'Build' },
    { id: 'code', label: 'Code', icon: 'Code' },
    { id: 'settings', label: 'Settings', icon: 'Settings' },
  ];

  return (
    <div className="flex flex-col h-screen max-w-md mx-auto bg-white dark:bg-[#121212] overflow-hidden shadow-2xl relative border-x border-gray-200 dark:border-gray-800">
      {/* Top Status Bar (Simulated) */}
      <div className="flex justify-between items-center px-6 py-4 bg-white dark:bg-[#121212] z-10 border-b border-gray-100 dark:border-gray-800">
        <h1 className="text-xl font-bold text-primary tracking-tight">Nextgens</h1>
        <div className="flex items-center gap-2 px-3 py-1 bg-gray-100 dark:bg-gray-800 rounded-full border border-gray-200 dark:border-gray-700">
          <div className={cn("w-2 h-2 rounded-full", isVpnConnected ? "bg-green-500 animate-pulse" : "bg-gray-400")} />
          <span className="text-xs font-medium text-gray-600 dark:text-gray-400">
            {isVpnConnected ? "VPN Connected" : "VPN Disconnected"}
          </span>
        </div>
      </div>

      {/* Main Content Area */}
      <div className="flex-1 overflow-y-auto pb-24 relative scrollbar-hide">
        <AnimatePresence mode="wait">
          <motion.div
            key={activeTab}
            initial={{ opacity: 0, y: 10 }}
            animate={{ opacity: 1, y: 0 }}
            exit={{ opacity: 0, y: -10 }}
            transition={{ duration: 0.2 }}
            className="p-6"
          >
            {activeTab === 'dashboard' && <Dashboard isConnected={isVpnConnected} setIsConnected={setIsVpnConnected} />}
            {activeTab === 'tools' && <Tools />}
            {activeTab === 'code' && <CodeExecutor />}
            {activeTab === 'settings' && <Settings />}
          </motion.div>
        </AnimatePresence>
      </div>

      {/* Bottom Navigation */}
      <div className="absolute bottom-0 left-0 right-0 bg-white/80 dark:bg-[#121212]/80 backdrop-blur-lg border-t border-gray-200 dark:border-gray-800 px-6 py-4 flex justify-between items-center z-20">
        {navItems.map((item) => {
          const Icon = ICON_MAP[item.icon];
          const isActive = activeTab === item.id;
          return (
            <button
              key={item.id}
              onClick={() => setActiveTab(item.id as Screen)}
              className="flex flex-col items-center gap-1 group relative outline-none"
              id={`nav-${item.id}`}
            >
              <div className={cn(
                "p-2 rounded-xl transition-all duration-300",
                isActive 
                  ? "bg-primary text-white scale-110 shadow-lg shadow-primary/30" 
                  : "text-gray-400 group-hover:text-primary group-hover:bg-primary/5"
              )}>
                <Icon size={24} strokeWidth={isActive ? 2.5 : 2} />
              </div>
              <span className={cn(
                "text-[10px] font-bold uppercase tracking-wider transition-colors duration-300",
                isActive ? "text-primary" : "text-gray-400"
              )}>
                {item.label}
              </span>
              {isActive && (
                <motion.div 
                  layoutId="activeIndicator"
                  className="absolute -bottom-1 w-1 h-1 bg-primary rounded-full"
                />
              )}
            </button>
          );
        })}
      </div>
    </div>
  );
}

