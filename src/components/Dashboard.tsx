import React, { useState, useEffect } from 'react';
import { motion } from 'motion/react';
import { 
  Wifi, 
  Globe, 
  Smartphone, 
  Zap, 
  Power,
  RefreshCw
} from 'lucide-react';
import { AreaChart, Area, XAxis, YAxis, ResponsiveContainer, Tooltip } from 'recharts';
import { cn } from '../lib/utils';
import { NetworkStats } from '../types';

interface DashboardProps {
  isConnected: boolean;
  setIsConnected: (val: boolean) => void;
}

const generateMockData = (): NetworkStats[] => {
  return Array.from({ length: 20 }).map((_, i) => ({
    timestamp: `${i}:00`,
    download: Math.floor(Math.random() * 80) + 20,
    upload: Math.floor(Math.random() * 40) + 10,
  }));
};

export default function Dashboard({ isConnected, setIsConnected }: DashboardProps) {
  const [stats, setStats] = useState<NetworkStats[]>(generateMockData());

  useEffect(() => {
    const interval = setInterval(() => {
      setStats(prev => {
        const newData = [...prev.slice(1), {
          timestamp: 'Now',
          download: Math.floor(Math.random() * 80) + 20,
          upload: Math.floor(Math.random() * 40) + 10,
        }];
        return newData;
      });
    }, 2000);
    return () => clearInterval(interval);
  }, []);

  const quickTiles = [
    { label: 'Hotspot', icon: Wifi, color: 'bg-blue-50 text-blue-600' },
    { label: 'Mesh', icon: Smartphone, color: 'bg-indigo-50 text-indigo-600' },
    { label: 'Global Share', icon: Globe, color: 'bg-cyan-50 text-cyan-600' },
    { label: '4G Lite', icon: Zap, color: 'bg-amber-50 text-amber-600' },
  ];

  return (
    <div className="space-y-6">
      {/* VPN Connect Button */}
      <div className="flex flex-col items-center py-8 relative">
        <motion.div
          animate={isConnected ? { scale: [1, 1.1, 1] } : {}}
          transition={{ repeat: Infinity, duration: 3 }}
          className={cn(
            "w-48 h-48 rounded-full flex items-center justify-center relative cursor-pointer group transition-all duration-500",
            isConnected ? "bg-primary shadow-[0_0_50px_rgba(26,35,126,0.3)]" : "bg-gray-100 dark:bg-gray-800"
          )}
          onClick={() => setIsConnected(!isConnected)}
          id="vpn-connector"
        >
          {/* Animated Rings */}
          <div className={cn(
            "absolute inset-0 rounded-full border-2 border-primary/20 transition-all duration-700",
            isConnected ? "scale-125 opacity-0 animate-ping" : "scale-100 opacity-100"
          )} />
          
          <div className="flex flex-col items-center gap-2">
            <Power size={48} className={isConnected ? "text-white" : "text-gray-400 group-hover:text-primary transition-colors"} />
            <span className={cn(
              "text-sm font-black uppercase tracking-widest",
              isConnected ? "text-white" : "text-gray-500"
            )}>
              {isConnected ? "Connected" : "Connect"}
            </span>
          </div>
        </motion.div>
      </div>

      {/* Quick Tiles */}
      <div className="grid grid-cols-2 gap-4">
        {quickTiles.map((tile) => (
          <motion.button
            key={tile.label}
            whileHover={{ scale: 1.02 }}
            whileTap={{ scale: 0.98 }}
            className="flex items-center gap-4 p-4 bg-white dark:bg-gray-800 border border-gray-100 dark:border-gray-700 rounded-xl shadow-sm hover:shadow-md transition-all group"
            id={`tile-${tile.label.toLowerCase().replace(' ', '-')}`}
          >
            <div className={cn("p-2.5 rounded-lg transition-colors", tile.color)}>
              <tile.icon size={20} />
            </div>
            <span className="font-semibold text-gray-700 dark:text-gray-200">{tile.label}</span>
          </motion.button>
        ))}
      </div>

      {/* Live Speed Graph */}
      <div className="bg-white dark:bg-gray-800 p-5 rounded-xl border border-gray-100 dark:border-gray-700 shadow-sm">
        <div className="flex justify-between items-center mb-6">
          <h3 className="font-bold text-gray-800 dark:text-gray-100">Live Network Speed</h3>
          <div className="flex gap-4 text-[10px] font-bold uppercase tracking-wider">
            <div className="flex items-center gap-1 text-primary">
              <div className="w-2 h-2 rounded-full bg-primary" /> Download
            </div>
            <div className="flex items-center gap-1 text-accent">
              <div className="w-2 h-2 rounded-full bg-accent" /> Upload
            </div>
          </div>
        </div>
        <div className="h-40 w-full">
          <ResponsiveContainer width="100%" height="100%">
            <AreaChart data={stats}>
              <defs>
                <linearGradient id="colorDown" x1="0" y1="0" x2="0" y2="1">
                  <stop offset="5%" stopColor="#1A237E" stopOpacity={0.2}/>
                  <stop offset="95%" stopColor="#1A237E" stopOpacity={0}/>
                </linearGradient>
                <linearGradient id="colorUp" x1="0" y1="0" x2="0" y2="1">
                  <stop offset="5%" stopColor="#00BCD4" stopOpacity={0.2}/>
                  <stop offset="95%" stopColor="#00BCD4" stopOpacity={0}/>
                </linearGradient>
              </defs>
              <Area type="monotone" dataKey="download" stroke="#1A237E" fillOpacity={1} fill="url(#colorDown)" strokeWidth={2} isAnimationActive={false} />
              <Area type="monotone" dataKey="upload" stroke="#00BCD4" fillOpacity={1} fill="url(#colorUp)" strokeWidth={2} isAnimationActive={false} />
              <Tooltip 
                contentStyle={{ backgroundColor: '#1f2937', color: '#fff', borderRadius: '12px', border: 'none' }}
                itemStyle={{ fontSize: '12px' }}
              />
            </AreaChart>
          </ResponsiveContainer>
        </div>
      </div>

      {/* Current Network Card */}
      <div className="bg-white dark:bg-gray-800 p-5 rounded-xl border border-gray-100 dark:border-gray-700 shadow-sm">
        <div className="flex justify-between items-center mb-4">
          <div className="flex items-center gap-3">
            <div className="p-2 bg-primary/5 text-primary rounded-lg">
              <Wifi size={20} />
            </div>
            <div>
              <h4 className="text-xs text-gray-500 font-medium">Current Network</h4>
              <p className="font-bold text-gray-800 dark:text-gray-100">Nextgens_Secure_Wifi</p>
            </div>
          </div>
          <button className="p-2 hover:bg-gray-100 dark:hover:bg-gray-700 rounded-full transition-colors text-gray-400">
            <RefreshCw size={18} />
          </button>
        </div>
        
        <div className="grid grid-cols-2 gap-y-4 pt-4 border-t border-gray-50 dark:border-gray-700">
          <div>
            <span className="block text-[10px] text-gray-500 font-bold uppercase">IP Address</span>
            <span className="text-sm font-medium dark:text-gray-300">192.168.1.15</span>
          </div>
          <div>
            <span className="block text-[10px] text-gray-500 font-bold uppercase">Signal Strength</span>
            <span className="text-sm font-medium text-green-500">Excellent</span>
          </div>
          <div>
            <span className="block text-[10px] text-gray-500 font-bold uppercase">Interface</span>
            <span className="text-sm font-medium dark:text-gray-300">wlan0 (WireGuard)</span>
          </div>
          <div>
            <span className="block text-[10px] text-gray-500 font-bold uppercase">DNS</span>
            <span className="text-sm font-medium dark:text-gray-300">8.8.8.8, 8.8.4.4</span>
          </div>
        </div>

        <button className="w-full mt-6 py-3 bg-primary/5 hover:bg-primary/10 text-primary font-bold rounded-lg transition-all text-xs uppercase tracking-widest border border-primary/10">
          Scan Nearby Networks
        </button>
      </div>
    </div>
  );
}
