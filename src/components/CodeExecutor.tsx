import React, { useState, useRef, useEffect } from 'react';
import { motion } from 'motion/react';
import { Play, Square, Terminal, Code2, Trash2, Smartphone } from 'lucide-react';
import { cn } from '../lib/utils';

export default function CodeExecutor() {
  const [code, setCode] = useState(
`# Nextgens Mobile Shell Script v1.0
echo "Initializing network diagnostics..."
service vpn status
iptables -L -n
echo "Optimizing packet routes..."
sysctl -w net.ipv4.ip_forward=1
echo "Ready."`);

  const [output, setOutput] = useState<string[]>([]);
  const [isRunning, setIsRunning] = useState(false);
  const consoleRef = useRef<HTMLDivElement>(null);

  const templates = [
    { name: 'Flush DNS', cmd: 'ndc resolver flushdefaultif' },
    { name: 'Reset iptables', cmd: 'iptables -F && iptables -t nat -F' },
    { name: 'Kill Apps', cmd: 'am kill-all' },
    { name: 'Speed Test', cmd: 'nextgens-cli speedtest --start' }
  ];

  const handleRun = () => {
    setIsRunning(true);
    setOutput(prev => [...prev, `> Executing script... ${new Date().toLocaleTimeString()}`]);
    
    // Simulate execution steps
    const lines = code.split('\n').filter(l => l.trim() && !l.startsWith('#'));
    lines.forEach((line, i) => {
      setTimeout(() => {
        setOutput(prev => [...prev, `[system] ${line}`]);
        if (i === lines.length - 1) {
          setIsRunning(false);
          setOutput(prev => [...prev, `Done.`]);
        }
      }, (i + 1) * 800);
    });
  };

  useEffect(() => {
    if (consoleRef.current) {
      consoleRef.current.scrollTop = consoleRef.current.scrollHeight;
    }
  }, [output]);

  return (
    <div className="space-y-6">
      {/* Editor Section */}
      <div className="bg-[#1e1e1e] rounded-xl overflow-hidden shadow-xl border border-gray-800">
        <div className="flex items-center justify-between px-4 py-2 bg-[#252525] border-b border-gray-800">
          <div className="flex items-center gap-2">
            <Terminal size={14} className="text-gray-400" />
            <span className="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Editor (Shell)</span>
          </div>
          <div className="flex gap-1">
            <div className="w-2.5 h-2.5 rounded-full bg-red-500/50" />
            <div className="w-2.5 h-2.5 rounded-full bg-amber-500/50" />
            <div className="w-2.5 h-2.5 rounded-full bg-green-500/50" />
          </div>
        </div>
        <textarea
          value={code}
          onChange={(e) => setCode(e.target.value)}
          className="w-full h-48 bg-transparent p-4 text-xs font-mono text-cyan-400 outline-none resize-none spellcheck-false"
          spellCheck={false}
        />
      </div>

      {/* Control Buttons */}
      <div className="flex items-center gap-2 overflow-x-auto pb-2 scrollbar-hide">
        {templates.map(t => (
          <button 
            key={t.name}
            onClick={() => setCode(prev => prev + '\n' + t.cmd)}
            className="flex-shrink-0 px-3 py-1.5 bg-gray-100 dark:bg-gray-800 hover:bg-primary/10 hover:text-primary rounded-lg text-[10px] font-bold text-gray-500 dark:text-gray-400 transition-colors uppercase border border-transparent hover:border-primary/20"
          >
            {t.name}
          </button>
        ))}
      </div>

      <div className="flex gap-4">
        <button
          onClick={handleRun}
          disabled={isRunning}
          className={cn(
            "flex-1 flex items-center justify-center gap-2 py-3 rounded-xl font-bold text-sm tracking-wide transition-all shadow-lg",
            isRunning ? "bg-gray-100 dark:bg-gray-800 text-gray-400 cursor-not-allowed" : "bg-primary text-white shadow-primary/30 active:scale-95"
          )}
        >
          <Play size={18} /> Run Script
        </button>
        <button
          onClick={() => { setOutput([]); setIsRunning(false); }}
          className="p-3 rounded-xl bg-gray-100 dark:bg-gray-800 text-gray-500 hover:text-red-500 transition-colors border border-gray-200 dark:border-gray-700"
        >
          <Trash2 size={18} />
        </button>
      </div>

      {/* Output Console */}
      <div className="bg-black/95 rounded-xl border border-gray-800 p-4 font-mono text-[11px]">
        <div className="flex items-center gap-2 mb-3 text-gray-500">
          <Code2 size={12} />
          <span className="uppercase text-[9px] font-bold tracking-widest">Console Output</span>
        </div>
        <div 
          ref={consoleRef}
          className="h-32 overflow-y-auto space-y-1 scrollbar-hide text-green-500/90"
        >
          {output.length === 0 ? (
            <span className="text-gray-700">Waiting for execution...</span>
          ) : (
            output.map((line, i) => (
              <div key={i} className="flex gap-2">
                <span className="text-gray-700 select-none">$</span>
                <span className="break-all">{line}</span>
              </div>
            ))
          )}
        </div>
      </div>

      {/* Device Info (Root Status) */}
      <div className="p-4 bg-amber-500/5 border border-amber-500/20 rounded-xl flex items-center justify-between">
        <div className="flex items-center gap-3">
          <Smartphone size={20} className="text-amber-500" />
          <div>
            <p className="text-xs font-bold text-amber-900 dark:text-amber-200">Root Access Detected</p>
            <p className="text-[10px] text-amber-700/70 dark:text-amber-400/50">Full shell privileges enabled for Nextgens</p>
          </div>
        </div>
        <div className="w-2 h-2 rounded-full bg-green-500 shadow-[0_0_8px_rgba(34,197,94,0.5)]" />
      </div>
    </div>
  );
}
