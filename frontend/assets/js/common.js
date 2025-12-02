// assets/js/common.js
const API_BASE = 'http://localhost:8080';

function setToken(token) { localStorage.setItem('cms_token', token); }
function getToken() { return localStorage.getItem('cms_token'); }
function clearToken() { localStorage.removeItem('cms_token'); }

function authHeader() {
  const t = getToken();
  return t ? { 'Authorization': 'Bearer ' + t } : {};
}

async function apiFetch(path, opts = {}) {
  const headers = opts.headers || {};
  Object.assign(headers, authHeader());
  if (opts.body && !(opts.body instanceof FormData) && !headers['Content-Type']) {
    headers['Content-Type'] = 'application/json';
  }
  const res = await fetch(API_BASE + path, { ...opts, headers });
  if (res.status === 401) {
    alert('Unauthorized — please login again.');
    clearToken();
    window.location = '/login.html';
    throw new Error('Unauthorized');
  }
  if (res.status === 403) {
    alert('Forbidden — you do not have permission.');
    throw new Error('Forbidden');
  }
  if (!res.ok) {
    const text = await res.text();
    throw new Error(res.status + ' ' + text);
  }
  if (res.status === 204) return null;
  const ct = res.headers.get('content-type') || '';
  if (ct.includes('application/json')) return res.json();
  return res.text();
}

function parseJwt(token) {
  try {
    const base = token.split('.')[1];
    const json = atob(base.replace(/-/g,'+').replace(/_/g,'/'));
    return JSON.parse(decodeURIComponent(escape(json)));
  } catch (e) { return null; }
}

function getUserRoles() {
  const t = getToken(); if (!t) return [];
  const p = parseJwt(t); if (!p) return [];
  const rolesClaim = p.roles || p.role || p.authorities || '';
  if (Array.isArray(rolesClaim)) return rolesClaim.map(r => r.replace(/^ROLE_/, ''));
  return rolesClaim.toString().split(',').map(s => s.trim().replace(/^ROLE_/, ''));
}

function hasRole(role) {
  return getUserRoles().includes(role);
}
function getUserId() {
  const t = getToken();
  if (!t) return null;
  const p = parseJwt(t);
  return p ? p.id : null; // This will now work!
}

function initCommonNav() {
  const userLabel = document.getElementById('userLabel');
  const logoutBtn = document.getElementById('btnLogout');
  const rolesSpan = document.getElementById('userRoles');
  if (getToken()) {
    const p = parseJwt(getToken());
    userLabel && (userLabel.textContent = p?.sub || 'User');
    rolesSpan && (rolesSpan.textContent = getUserRoles().join(', '));
    logoutBtn && (logoutBtn.style.display = 'inline-block');
  } else {
    userLabel && (userLabel.textContent = '');
    rolesSpan && (rolesSpan.textContent = '');
    logoutBtn && (logoutBtn.style.display = 'none');
  }
  if (logoutBtn) logoutBtn.addEventListener('click', () => {
    clearToken();
    window.location = '/login.html';
  });
}
